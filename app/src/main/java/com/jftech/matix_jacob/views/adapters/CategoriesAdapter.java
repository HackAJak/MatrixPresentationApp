package com.jftech.matix_jacob.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.models.SpecialOffer;
import com.jftech.matix_jacob.data.services.MainActivityViewModel;
import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>
{
    private final String[] categories;
    private final SpecialOffer[] specialOffers;
    private final Context context;
    private final MainActivityViewModel viewModelReference;
    private final boolean snapViews;

    public CategoriesAdapter(String[] categories, SpecialOffer[] specialOffers, Context context, MainActivityViewModel viewModel, boolean snapViews)
    {
        this.categories = categories;
        this.specialOffers = specialOffers;
        this.context = context;
        viewModelReference = viewModel;
        this.snapViews = snapViews;
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
            if (offer.getCatId() == position)
                categoryOffers.add(offer);
        }
        holder.titleTextView.setText(categories[position]);
        //add adapter edits here
        if (snapViews)
        {
            new PagerSnapHelper().attachToRecyclerView(holder.specialOffersRecyclerView);
        }
        else
        {
            holder.specialOffersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
                {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                        snapToClosestView(recyclerView);
                    else if (newState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
                        snapToClosestView(recyclerView);
                }
            });
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        holder.specialOffersRecyclerView.setLayoutManager(layoutManager);
        holder.specialOffersRecyclerView.setAdapter(new SpecialOffersAdapter(categoryOffers.toArray(new SpecialOffer[0]), viewModelReference));
    }

    @Override
    public int getItemCount()
    {
        return categories.length;
    }

    private void snapToClosestView(RecyclerView recyclerView)
    {
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
        int itemCount = this.getItemCount();
        View view = recyclerView.getLayoutManager().getChildAt(0);
        if (firstVisibleItemPosition > 0 && view != null)
            if (itemCount > 0)
                if (lastCompletelyVisibleItemPosition != -1)
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(lastCompletelyVisibleItemPosition, 5);
                else if (firstCompletelyVisibleItemPosition != -1)
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(firstCompletelyVisibleItemPosition, 5);
                else
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(firstVisibleItemPosition, 5);
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
