package com.jftech.matrix_jacob.data.services;

import android.content.SharedPreferences;

public class SharedPreferencesManager
{
    private static SharedPreferencesManager sharedInstance = null;
    public static SharedPreferencesManager SharedInstance()
    {
        if (sharedInstance == null)
        {
            sharedInstance = new SharedPreferencesManager();
        }
        return sharedInstance;
    }

    private SharedPreferences sharedPreferences;
    public SharedPreferences getSharedPreferences()
    {
        return sharedPreferences;
    }

    public void Init(SharedPreferences sharedPreferences)
    {
        this.sharedPreferences = sharedPreferences;
    }
}
