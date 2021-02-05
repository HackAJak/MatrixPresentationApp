package com.jftech.matrix_jacob.utilities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Locale;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class Localization
{
    private static final String Default_Locale = "en";
    public static Map<String, String> HebrewTabTitleToEnglish = Map.of("המועדפים","Favourites", "הפינוקים שלי", "Spoils", "כל ההטבות", "all", "המומלצים", "recommended");

    public static Context SetDefaultLocaleForCorrectOrientation(Context context)
    {
        Locale defaultLocale = new Locale(Default_Locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(defaultLocale);
        return context.createConfigurationContext(configuration);
    }
}
