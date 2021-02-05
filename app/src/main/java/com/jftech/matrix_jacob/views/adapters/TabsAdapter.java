package com.jftech.matrix_jacob.views.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jftech.matrix_jacob.R;
import com.jftech.matrix_jacob.data.services.MainActivityViewModel;
import com.jftech.matrix_jacob.utilities.Localization;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.TabViewHolder>
{
    private final String[] tabs;
    private final MainActivityViewModel viewModelReference;
    private final Context context;


    public TabsAdapter(String[] tabs, MainActivityViewModel viewModel, Context context)
    {
        this.tabs = tabs;
        viewModelReference = viewModel;
        this.context = context;
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
        holder.getTabTitleTextView().setText(tabs[position]);
        if (viewModelReference.getSelectedTabViewIndex() == position)
            holder.getTabTitleTextView().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.drawable_selected_tab));
        holder.getTabTitleTextView().setOnClickListener(view ->
        {
            viewModelReference.setSelectedTab(Localization.HebrewTabTitleToEnglish.get(tabs[position]));
            viewModelReference.setSelectedTabViewIndex(position);
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
