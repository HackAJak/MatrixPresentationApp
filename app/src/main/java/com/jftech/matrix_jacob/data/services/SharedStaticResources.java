package com.jftech.matrix_jacob.data.services;

import java.io.InputStream;

public class SharedStaticResources
{
    private static SharedStaticResources sharedInstance = null;
    public static SharedStaticResources SharedInstance()
    {
        if (sharedInstance == null)
        {
            sharedInstance = new SharedStaticResources();
        }
        return sharedInstance;
    }

    private InputStream jsonSource;
    public InputStream getJsonSource()
    {
        return jsonSource;
    }
    public void setJsonSource(InputStream jsonSource)
    {
        this.jsonSource = jsonSource;
    }
}
