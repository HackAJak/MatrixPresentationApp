package com.jftech.matix_jacob.views.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.services.MainActivityViewModel;
import com.jftech.matix_jacob.utilities.Localization;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.TabViewHolder>
{
    private final String[] tabs;
    private final MainActivityViewModel viewModelReference;


    public TabsAdapter(String[] tabs, MainActivityViewModel viewModel)
    {
        this.tabs = tabs;
        viewModelReference = viewModel;
    }



    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tab, parent, false);
        return new TabViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull TabViewHolder holder, int position)
    {
        holder.tabTitleTextView.setText(tabs[position]);
        holder.tabTitleTextView.setOnClickListener(view ->
        {
            viewModelReference.setSelectedTab(Localization.HebrewTabTitleToEnglish.get(tabs[position]));
            viewModelReference.setSelectedTabView(position);
        });
    }

    @Override
    public int getItemCount()
    {
        return tabs.length;
    }

    public static class TabViewHolder extends RecyclerView.ViewHolder
    {
        private final AppCompatTextView tabTitleTextView;
        public AppCompatTextView getTabTitleTextView()
        {
            return tabTitleTextView;
        }

        public TabViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tabTitleTextView = itemView.findViewById(R.id.tab_title_textview);
        }
    }
}
