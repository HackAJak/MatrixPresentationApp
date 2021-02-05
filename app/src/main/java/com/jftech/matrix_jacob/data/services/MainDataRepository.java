package com.jftech.matrix_jacob.data.services;

import android.content.Context;
import android.widget.Toast;

import com.jftech.matrix_jacob.R;
import com.jftech.matrix_jacob.data.models.SpecialOffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainDataRepository
{
    private static final String mainDataKey = "main_data";
    private static final String categoryDataKey = "category_data";



    public static void RequestData(MainActivityViewModel viewModel)
    {
        if (SharedPreferencesManager.SharedInstance().getSharedPreferences().contains(mainDataKey))
            mainDataFromLocal(viewModel);
        else
            mainDataFromServer(viewModel);
    }

    public static void RequestCategories(MainActivityViewModel viewModel)
    {
        if (SharedPreferencesManager.SharedInstance().getSharedPreferences().contains(categoryDataKey))
            categoryDataFromLocal(viewModel);
        else
            categoryDataFromServer(viewModel);
    }



    private static void mainDataFromServer(MainActivityViewModel viewModel)
    {
        new Thread(() ->
        {
            JSONObject json = null;
            JSONArray jsonOffers = new JSONArray();
            try
            {
                json = new JSONObject(simulateNetworkRequest(viewModel));
                jsonOffers = json.getJSONObject("DataObject").getJSONArray("DataListObject");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                viewModel.setErrorToRaise(e);
            }
            SpecialOffer[] parsedOffers = parseOffersJsonArray(jsonOffers, viewModel);
            if (parsedOffers.length > 0)
                SharedPreferencesManager.SharedInstance().getSharedPreferences().edit().putString(mainDataKey ,jsonOffers.toString()).apply();
            viewModel.setMainData(parsedOffers);
        }).start();
    }

    private static void mainDataFromLocal(MainActivityViewModel viewModel)
    {
        String jsonArrayString = SharedPreferencesManager.SharedInstance().getSharedPreferences().getString(mainDataKey, null);
        SpecialOffer[] parsedOffers = new SpecialOffer[0];
        try
        {
            parsedOffers = parseOffersJsonArray(new JSONArray(jsonArrayString), viewModel);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            viewModel.setErrorToRaise(e);
        }
        viewModel.setMainData(parsedOffers);
    }

    private static void categoryDataFromServer(MainActivityViewModel viewModel)
    {
        new Thread(() ->
        {
            JSONObject json = null;
            JSONArray jsonCategories = new JSONArray();
            try
            {
                json = new JSONObject(simulateNetworkRequest(viewModel));
                jsonCategories = json.getJSONObject("DataObject").getJSONArray("DataListCat");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                viewModel.setErrorToRaise(e);
            }
            Map<Integer, String> parsedCategories = parseCategoriesJsonArray(jsonCategories, viewModel);
            if (parsedCategories.size() > 0)
                SharedPreferencesManager.SharedInstance().getSharedPreferences().edit().putString(categoryDataKey ,jsonCategories.toString()).apply();
            viewModel.setCategories(parsedCategories);
        }).start();
    }

    private static void categoryDataFromLocal(MainActivityViewModel viewModel)
    {
        String jsonArrayString = SharedPreferencesManager.SharedInstance().getSharedPreferences().getString(categoryDataKey, null);
        Map<Integer, String> parsedCategories = new HashMap<Integer, String>();
        try
        {
            parsedCategories = parseCategoriesJsonArray(new JSONArray(jsonArrayString), viewModel);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            viewModel.setErrorToRaise(e);
        }
        viewModel.setCategories(parsedCategories);
    }

    private static SpecialOffer[] parseOffersJsonArray(JSONArray jsonData, MainActivityViewModel viewModel)
    {
        ArrayList<SpecialOffer> specialOffers = new ArrayList<SpecialOffer>();
        try
        {
            for (int i = 0; i < jsonData.length(); i++)
                specialOffers.add(new SpecialOffer(jsonData.getJSONObject(i)));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            viewModel.setErrorToRaise(e);
        }
        return specialOffers.toArray(new SpecialOffer[0]);
    }

    private static Map<Integer, String> parseCategoriesJsonArray(JSONArray jsonData, MainActivityViewModel viewModel)
    {
        Map<Integer, String> categories = new HashMap<Integer, String>();
        try
        {
            for (int i = 0; i < jsonData.length(); i++)
            {
                Integer id = jsonData.getJSONObject(i).getInt("CatId");
                String cat = jsonData.getJSONObject(i).getString("CTitle");
                categories.put(id, cat);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            viewModel.setErrorToRaise(e);
        }
        return categories;
    }

    private static String simulateNetworkRequest(MainActivityViewModel viewModel)
    {
        InputStream stream = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            String jsonString = null;
            stream = SharedStaticResources.SharedInstance().getJsonSource();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            while ((jsonString = reader.readLine()) != null)
                builder.append(jsonString);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stream != null)
            {
                try
                {
                    stream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    viewModel.setErrorToRaise(e);
                }
            }
        }
        return new String(builder);
    }
}
