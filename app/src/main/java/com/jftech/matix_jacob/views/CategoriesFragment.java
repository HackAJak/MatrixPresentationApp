package com.jftech.matix_jacob.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.services.MainActivityViewModel;
import com.jftech.matix_jacob.data.services.MainDataRepository;
import com.jftech.matix_jacob.views.adapters.CategoriesAdapter;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;


public class CategoriesFragment extends Fragment
{
    private String adapterType;
    private MainActivityViewModel viewModelReference;
    private RecyclerView categoriesRecyclerView;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null)
            adapterType = arguments.getString("adapter_type");
        else
            adapterType = "all";
        viewModelReference = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        view.post(() ->
        {
            wireViews(view);
            viewModelReference.getMainData().observe(getViewLifecycleOwner(), specialOffers ->
            {
                categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                boolean snapViews = adapterType != "all";
                categoriesRecyclerView.setAdapter(new CategoriesAdapter(viewModelReference.getCategoryNames(), viewModelReference.getMainData().getValue(), requireContext(), viewModelReference, snapViews));
            });
            MainDataRepository.RequestData(requireContext(), viewModelReference);
        });
    }

    private void wireViews(View view)
    {
        categoriesRecyclerView = view.findViewById(R.id.categories_recyclerview);
    }
}
