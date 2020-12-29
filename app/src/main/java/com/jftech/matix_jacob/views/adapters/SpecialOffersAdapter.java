package com.jftech.matix_jacob.views.adapters;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.models.SpecialOffer;
import com.jftech.matix_jacob.data.services.MainActivityViewModel;
import com.squareup.picasso.Picasso;

public class SpecialOffersAdapter extends RecyclerView.Adapter<SpecialOffersAdapter.SpecialOfferViewHolder>
{
    private final SpecialOffer[] specialOffers;
    private final MainActivityViewModel viewModelReference;


    public  SpecialOffersAdapter(SpecialOffer[] specialOffers, MainActivityViewModel viewModel)
    {
        this.specialOffers = specialOffers;
        viewModelReference = viewModel;
    }



    @NonNull
    @Override
    public SpecialOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_special_offer, parent, false);
        return new SpecialOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOfferViewHolder holder, int position)
    {
        SpecialOffer specialOffer = specialOffers[position];
        //Build the "fancy" title string
        SpannableString title = new SpannableString(specialOffer.getTitle());
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        title.setSpan(bold, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence composedTitle = TextUtils.concat(title, new SpannableString(" - " + specialOffer.getSubTitle()));
        holder.titleTextView.setText(composedTitle);
        //Get the image using picasso
        Picasso.get()
                .load(specialOffer.getImage())
                .placeholder(R.drawable.drawable_placeholder)
                .error(R.drawable.drawable_error)
                .into(holder.imageImageView);
        //Set onClick listener to notify the Activity by updating the viewModel
        holder.imageImageView.setOnClickListener(view ->
        {
            viewModelReference.setSelectedOffer(specialOffer);
        });
    }

    @Override
    public int getItemCount()
    {
        return specialOffers.length;
    }

    public static class SpecialOfferViewHolder extends RecyclerView.ViewHolder
    {
        private final AppCompatImageView imageImageView;
        public AppCompatImageView getImageImageView()
        {
            return imageImageView;
        }

        private final AppCompatTextView titleTextView;
        public AppCompatTextView getTitleTextView()
        {
            return titleTextView;
        }

        public SpecialOfferViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageImageView = itemView.findViewById(R.id.special_offer_layout_image_imageview);
            titleTextView = itemView.findViewById(R.id.special_offer_layout_title);
        }
    }
}
