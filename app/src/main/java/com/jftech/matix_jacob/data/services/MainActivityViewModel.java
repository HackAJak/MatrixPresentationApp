package com.jftech.matix_jacob.data.services;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.jftech.matix_jacob.data.models.SpecialOffer;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivityViewModel extends ViewModel
{
    private Map<Integer, String> Categories;
    public void setCategories(Map<Integer, String> categories)
    {
        Categories = categories;
        buildCategoryNames();
    }
    public Map<Integer, String> getCategories()
    {
        return Categories;
    }

    private String[] CategoryNames = new String[0];
    public String[] getCategoryNames()
    {
        return CategoryNames;
    }

    private final MutableLiveData<SpecialOffer[]> MainData = new MutableLiveData<SpecialOffer[]>(new SpecialOffer[0]);
    public void setMainData(SpecialOffer[] mainData)
    {
        MainData.postValue(mainData);
    }
    public LiveData<SpecialOffer[]> getMainData()
    {
        return (LiveData<SpecialOffer[]>)MainData;
    }

    private MutableLiveData<SpecialOffer> SelectedOffer = new MutableLiveData<SpecialOffer>(null);
    public void setSelectedOffer(@Nullable SpecialOffer newSpecialOffer)
    {
        SelectedOffer.postValue(newSpecialOffer);
    }
    public LiveData<SpecialOffer> getSelectedOffer()
    {
        return (LiveData<SpecialOffer>)SelectedOffer;
    }

    private final MutableLiveData<String> SelectedTab = new MutableLiveData<String>("all");
    public void setSelectedTab(String selectedTab)
    {
        SelectedTab.postValue(selectedTab);
    }
    public LiveData<String> getSelectedTab()
    {
        return (LiveData<String>) SelectedTab;
    }

    private int SelectedTabView = -1;
    public void setSelectedTabView(int selectedTabView)
    {
        SelectedTabView = selectedTabView;
    }
    public int getSelectedTabView()
    {
        return SelectedTabView;
    }

    private void buildCategoryNames()
    {
        ArrayList<String> categoriesList = new ArrayList<String>();
        for (int i = 0; i < Categories.size(); i++)
            categoriesList.add(Categories.get(i));
        CategoryNames = categoriesList.toArray(new String[0]);
    }
}
