package com.jftech.matrix_jacob.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jftech.matrix_jacob.R;
import com.jftech.matrix_jacob.data.services.MainActivityViewModel;
import com.jftech.matrix_jacob.views.adapters.TabsAdapter;

public class TabBarFragment extends Fragment
{
    private MainActivityViewModel viewModelReference;
    private RecyclerView tabsRecyclerView;
    private final String[] tabs =  { "כל ההטבות","המומלצים", "הפינוקים שלי", "המועדפים"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModelReference = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_tab_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        view.post(() ->
        {
            wireViews(view);
            tabsRecyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, true));
            tabsRecyclerView.setAdapter(new TabsAdapter(tabs, viewModelReference, requireContext()));
            viewModelReference.getSelectedTab().observe(getViewLifecycleOwner(), this::handleSelectedTab);
        });
    }

    private void wireViews(View view)
    {
        tabsRecyclerView = view.findViewById(R.id.tab_bar_tabs_recyclerview);
    }

    private void handleSelectedTab(String tabType)
    {
        tabsRecyclerView.setAdapter(new TabsAdapter(tabs, viewModelReference, requireContext()));
        tabsRecyclerView.scrollToPosition(viewModelReference.getSelectedTabViewIndex());
    }
}
