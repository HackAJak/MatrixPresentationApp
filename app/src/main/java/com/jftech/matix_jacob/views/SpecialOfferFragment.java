package com.jftech.matix_jacob.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.services.MainActivityViewModel;
import com.jftech.matix_jacob.data.services.MainDataRepository;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

public class SpecialOfferFragment extends Fragment
{
    private AppCompatImageView specialOfferImageImageView;
    private AppCompatTextView specialOfferCategoryTextView;
    private AppCompatTextView specialOfferIdTextView;
    private MainActivityViewModel viewModelReference;


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
        return inflater.inflate(R.layout.fragment_special_offer, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        view.post(() ->
        {
            wireViews(view);
            Picasso.get()
                    .load(viewModelReference.getSelectedOffer().getValue().getImage())
                    .placeholder(R.drawable.drawable_placeholder)
                    .error(R.drawable.drawable_error)
                    .into(specialOfferImageImageView);
            specialOfferCategoryTextView.setText(viewModelReference.getCategories().get(viewModelReference.getSelectedOffer().getValue().getCatId()));
            specialOfferIdTextView.setText(viewModelReference.getSelectedOffer().getValue().getId().toString());
        });
    }

    @Override
    public void onDestroyView()
    {
        viewModelReference.setSelectedOffer(null);
        super.onDestroyView();
    }

    private void wireViews(View view)
    {
        specialOfferImageImageView = view.findViewById(R.id.special_offer_fragment_image_imageview);
        specialOfferCategoryTextView = view.findViewById(R.id.special_offer_fragment_category_textview);
        specialOfferIdTextView = view.findViewById(R.id.special_offer_fragment_offer_id_textview);
    }
}
