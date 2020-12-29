package com.jftech.matix_jacob.data.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddressData
{
    private String Address;
    public void setAddress(String address)
    {
        Address = address;
    }
    public String getAddress()
    {
        return Address;
    }

    private String City;
    public void setCity(String city)
    {
        City = city;
    }
    public String getCity()
    {
        return City;
    }

    public AddressData()
    {
        Address = "";
        City = "";
    }
    public AddressData(JSONArray json) throws JSONException
    {
        if (json.length() != 0)
        {
            Address = json.getJSONObject(0).has("Addr") ? json.getJSONObject(0).getString("Addr") : "";
            City = json.getJSONObject(0).has("DAd") ? json.getJSONObject(0).getString("DAd") : "";
        }
        else
        {
            Address = "";
            City = "";
        }
    }
}
