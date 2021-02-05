package com.jftech.matrix_jacob.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.jftech.matrix_jacob.R;

public class PlaceholderFragment extends Fragment
{
    private String tabTitle;
    private AppCompatTextView titleTextView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null)
            tabTitle = arguments.getString("title");
        else
            tabTitle = "No Title";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_placeholder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        view.post(() ->
        {
            wireViews(view);
            titleTextView.setText(tabTitle);
        });
    }

    private void wireViews(View view)
    {
        titleTextView = view.findViewById(R.id.placeholder_title_textview);
    }
}
