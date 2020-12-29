package com.jftech.matix_jacob.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.services.MainActivityViewModel;
import com.jftech.matix_jacob.views.adapters.TabsAdapter;

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
            tabsRecyclerView.setAdapter(new TabsAdapter(tabs, viewModelReference));
            viewModelReference.getSelectedTab().observe(getViewLifecycleOwner(), this::handleSelectedTab);
        });
    }

    private void wireViews(View view)
    {
        tabsRecyclerView = view.findViewById(R.id.tab_bar_tabs_recyclerview);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void handleSelectedTab(String tabType)
    {
        if (viewModelReference.getSelectedTabView() != -1)
        {
            restoreDefaultBackground();
            ((TabsAdapter.TabViewHolder) tabsRecyclerView.getChildViewHolder(tabsRecyclerView.getChildAt(viewModelReference.getSelectedTabView()))).getTabTitleTextView().setBackgroundDrawable(requireContext().getDrawable(R.drawable.drawable_selected_tab));
            tabsRecyclerView.scrollToPosition(viewModelReference.getSelectedTabView());
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void restoreDefaultBackground()
    {
        for (int i = 0; i < tabs.length; i++)
            ((TabsAdapter.TabViewHolder)tabsRecyclerView.getChildViewHolder(tabsRecyclerView.getChildAt(i))).getTabTitleTextView().setBackgroundDrawable(requireContext().getDrawable(R.drawable.drawable_tab));
    }
}
