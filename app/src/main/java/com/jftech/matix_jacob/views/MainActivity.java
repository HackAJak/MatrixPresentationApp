package com.jftech.matix_jacob.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.models.SpecialOffer;
import com.jftech.matix_jacob.data.services.MainActivityViewModel;
import com.jftech.matix_jacob.data.services.MainDataRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private MainActivityViewModel viewModelReference;



    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModelReference = new ViewModelProvider(this).get(MainActivityViewModel.class);
        if (viewModelReference.getCategories() == null)
            MainDataRepository.RequestCategories(this, viewModelReference);
        setTabFragment();
        viewModelReference.getSelectedTab().observe(this, this::handleTabTypeChanged);
        viewModelReference.getSelectedOffer().observe(this, this::handleOfferSelected);
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
}