package com.jftech.matix_jacob.utilities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Dictionary;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class Localization
{
    public static Map<String, String> HebrewTabTitleToEnglish = Map.of("המועדפים","Favourites", "הפינוקים שלי", "Spoils", "כל ההטבות", "all", "המומלצים", "recommended");
}
