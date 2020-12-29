package com.jftech.matix_jacob.data.services;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.jftech.matix_jacob.R;
import com.jftech.matix_jacob.data.models.SpecialOffer;

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
    private static final String preferencesName = "matrix_local";
    private static final String mainDataKey = "main_data";
    private static final String categoryDataKey = "category_data";



    public static void RequestData(Context context, MainActivityViewModel viewModel)
    {
        if (context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).contains(mainDataKey))
            mainDataFromLocal(context, viewModel);
        else
            mainDataFromServer(context, viewModel);
    }

    public static void RequestCategories(Context context, MainActivityViewModel viewModel)
    {
        if (context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).contains(categoryDataKey))
            categoryDataFromLocal(context, viewModel);
        else
            categoryDataFromServer(context, viewModel);
    }



    private static void mainDataFromServer(Context context, MainActivityViewModel viewModel)
    {
        new Thread(() ->
        {
            JSONObject json = null;
            JSONArray jsonOffers = new JSONArray();
            try
            {
                json = new JSONObject(simulateNetworkRequest(context));
                jsonOffers = json.getJSONObject("DataObject").getJSONArray("DataListObject");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            SpecialOffer[] parsedOffers = parseOffersJsonArray(jsonOffers, context);
            if (parsedOffers.length > 0)
                context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).edit().putString(mainDataKey ,jsonOffers.toString()).apply();
            viewModel.setMainData(parsedOffers);
        }).start();
    }

    private static void mainDataFromLocal(Context context, MainActivityViewModel viewModel)
    {
        String jsonArrayString = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getString(mainDataKey, null);
        SpecialOffer[] parsedOffers = new SpecialOffer[0];
        try
        {
            parsedOffers = parseOffersJsonArray(new JSONArray(jsonArrayString), context);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        viewModel.setMainData(parsedOffers);
    }

    private static void categoryDataFromServer(Context context, MainActivityViewModel viewModel)
    {
        new Thread(() ->
        {
            JSONObject json = null;
            JSONArray jsonCategories = new JSONArray();
            try
            {
                json = new JSONObject(simulateNetworkRequest(context));
                jsonCategories = json.getJSONObject("DataObject").getJSONArray("DataListCat");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            Map<Integer, String> parsedCategories = parseCategoriesJsonArray(jsonCategories, context);
            if (parsedCategories.size() > 0)
                context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).edit().putString(categoryDataKey ,jsonCategories.toString()).apply();
            viewModel.setCategories(parsedCategories);
        }).start();
    }

    private static void categoryDataFromLocal(Context context, MainActivityViewModel viewModel)
    {
        String jsonArrayString = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getString(categoryDataKey, null);
        Map<Integer, String> parsedCategories = new HashMap<Integer, String>();
        try
        {
            parsedCategories = parseCategoriesJsonArray(new JSONArray(jsonArrayString), context);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        viewModel.setCategories(parsedCategories);
    }

    private static SpecialOffer[] parseOffersJsonArray(JSONArray jsonData, Context context)
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
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return specialOffers.toArray(new SpecialOffer[0]);
    }

    private static Map<Integer, String> parseCategoriesJsonArray(JSONArray jsonData, Context context)
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
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return categories;
    }

    private static String simulateNetworkRequest(Context context)
    {
        InputStream stream = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            String jsonString = null;
            stream = context.getResources().openRawResource(R.raw.json_object);
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
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return new String(builder);
    }
}
