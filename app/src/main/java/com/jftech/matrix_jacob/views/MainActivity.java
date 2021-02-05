package com.jftech.matrix_jacob.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.jftech.matrix_jacob.R;
import com.jftech.matrix_jacob.data.models.SpecialOffer;
import com.jftech.matrix_jacob.data.services.MainActivityViewModel;
import com.jftech.matrix_jacob.data.services.MainDataRepository;
import com.jftech.matrix_jacob.data.services.SharedPreferencesManager;
import com.jftech.matrix_jacob.data.services.SharedStaticResources;
import com.jftech.matrix_jacob.utilities.Localization;

public class MainActivity extends AppCompatActivity
{
    private MainActivityViewModel viewModelReference;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(Localization.SetDefaultLocaleForCorrectOrientation(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String preferencesName = "matrix_local";
        SharedPreferencesManager.SharedInstance().Init(getSharedPreferences(preferencesName, Context.MODE_PRIVATE));
        SharedStaticResources.SharedInstance().setJsonSource(getResources().openRawResource(R.raw.json_object));
        viewModelReference = new ViewModelProvider(this).get(MainActivityViewModel.class);
        if (viewModelReference.getCategories() == null)
            MainDataRepository.RequestCategories(viewModelReference);
        setTabFragment();
        handleObservers();
    }

    @Override
    public void onBackPressed()
    {
        if (viewModelReference.getSelectedOffer().getValue() != null)
            viewModelReference.setSelectedTab(viewModelReference.getSelectedTab().getValue());
        else
            super.onBackPressed();
    }

    private void setTabFragment()
    {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_tab_bar_container, new TabBarFragment());
        transaction.commit();
    }

    private void replaceContentFragment(Fragment fragment)
    {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, fragment);
        transaction.commit();
    }

    private void handleObservers()
    {
        viewModelReference.getSelectedTab().observe(this, this::handleTabTypeChanged);
        viewModelReference.getSelectedOffer().observe(this, this::handleOfferSelected);
        viewModelReference.getErrorToRaise().observe(this, this::handleDetectedError);
    }

    private void handleTabTypeChanged(String tabType)
    {
        switch (tabType)
        {
            case "all": case "recommended":
                CategoriesFragment categoriesFragment = new CategoriesFragment();
                Bundle categoriesBundle = new Bundle();
                categoriesBundle.putString("adapter_type", tabType);
                categoriesFragment.setArguments(categoriesBundle);
                replaceContentFragment(categoriesFragment);
                break;
            default:
                PlaceholderFragment placeholderFragment = new PlaceholderFragment();
                Bundle placeholderBundle = new Bundle();
                placeholderBundle.putString("title", tabType);
                placeholderFragment.setArguments(placeholderBundle);
                replaceContentFragment(placeholderFragment);
                break;
        }
    }

    private void handleOfferSelected(SpecialOffer offer)
    {
        if (offer != null)
            replaceContentFragment(new SpecialOfferFragment());
    }

    private void handleDetectedError(Exception exception)
    {
        if (exception != null)
        {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}