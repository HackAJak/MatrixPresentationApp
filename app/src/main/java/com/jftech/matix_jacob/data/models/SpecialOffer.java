package com.jftech.matix_jacob.data.models;

import org.json.JSONException;
import org.json.JSONObject;

public class SpecialOffer
{
    private int CatId;

    public void setCatId(int catId)
    {
        CatId = catId;
    }

    public Integer getCatId()
    {
        return CatId;
    }

    private String Title;
    public void setTitle(String title)
    {
        Title = title;
    }
    public String getTitle()
    {
        return Title;
    }

    private String SubTitle;
    public void setSubTitle(String subTitle)
    {
        this.SubTitle = subTitle;
    }
    public String getSubTitle()
    {
        return SubTitle;
    }

    private String Image;
    public void setImage(String image)
    {
        Image = image;
    }
    public String getImage()
    {
        return Image;
    }

    private int Id;
    public void setId(int id)
    {
        Id = id;
    }
    public Integer getId()
    {
        return Id;
    }

    private AddressData Address;
    public void setAddress(AddressData address)
    {
        Address = address;
    }
    public AddressData getAddress()
    {
        return Address;
    }



    public SpecialOffer(JSONObject json) throws JSONException
    {
        CatId = json.has("CatId") ? json.getInt("CatId"): 0;
        Title = json.has("Title") ? json.getString("Title") : "";
        SubTitle = json.has("STitle") ? json.getString("STitle") : "";
        Image = json.has("Imag") ? json.getString("Imag") : "";
        Id = json.has("Id") ? json.getInt("Id") : 0;
        Address = json.has("DataListAddr") ? new AddressData(json.getJSONArray("DataListAddr")) : new AddressData();
    }
}
