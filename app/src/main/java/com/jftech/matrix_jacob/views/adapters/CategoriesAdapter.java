package com.jftech.matrix_jacob.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import com.jftech.matrix_jacob.utilities.CalibratedLinearHelper;
import com.jftech.matrix_jacob.utilities.CalibratedSnapHelper;
import com.jftech.matrix_jacob.utilities.SnappySmoothLinearLayoutManager;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jftech.matrix_jacob.R;
import com.jftech.matrix_jacob.data.models.SpecialOffer;
import com.jftech.matrix_jacob.data.services.MainActivityViewModel;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>
{
    private final String[] categories;
    private final SpecialOffer[] specialOffers;
    private final Context context;
    private final MainActivityViewModel viewModelReference;
    private final boolean snapViews;
    private final int parentWindowWidth;
    private final int smallSeparatorMargin;

    public CategoriesAdapter(String[] categories, SpecialOffer[] specialOffers, Context context, MainActivityViewModel viewModel, boolean snapViews, int parentWindowWidth)
    {
        this.categories = categories;
        this.specialOffers = specialOffers;
        this.context = context;
        viewModelReference = viewModel;
        this.snapViews = snapViews;
        this.parentWindowWidth = parentWindowWidth;
        smallSeparatorMargin = (int)(parentWindowWidth * 0.02);
    }



    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position)
    {
        ArrayList<SpecialOffer> categoryOffers = new ArrayList<SpecialOffer>();
        for (SpecialOffer offer : specialOffers)
        {
            if (offer.getCatId() == (position + 1))
                categoryOffers.add(offer);
        }
        holder.titleTextView.setText(categories[position]);
        //paged adapter
        if (snapViews)
        {
            CalibratedSnapHelper snapHelper = new CalibratedSnapHelper();
            snapHelper.attachToRecyclerView(holder.specialOffersRecyclerView);
        }
        //snappy scroll adapter
        else
        {
            new CalibratedLinearHelper().attachToRecyclerView(holder.specialOffersRecyclerView);
        }
        holder.specialOffersRecyclerView.setLayoutManager(new SnappySmoothLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true, parentWindowWidth));
        holder.specialOffersRecyclerView.setAdapter(new SpecialOffersAdapter(categoryOffers.toArray(new SpecialOffer[0]), viewModelReference, smallSeparatorMargin));
    }

    @Override
    public int getItemCount()
    {
        return categories.length;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        private final AppCompatTextView titleTextView;
        public AppCompatTextView getTitleTextView()
        {
            return titleTextView;
        }

        private final RecyclerView specialOffersRecyclerView;
        public RecyclerView getSpecialOffersRecyclerView()
        {
            return specialOffersRecyclerView;
        }

        public CategoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.category_layout_title_textview);
            specialOffersRecyclerView = itemView.findViewById(R.id.category_layout_special_offers_recyclerview);
        }
    }
}
