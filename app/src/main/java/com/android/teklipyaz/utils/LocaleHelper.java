package com.android.teklipyaz.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.teklipyaz.views.fragments.SettingsFragment;

import java.util.Locale;

public class LocaleHelper {

    public static String DEFAULT_LANGUAGE = "en";

    public static void setLocale(Context c) {
        setNewLocale(c, getLanguage(c));
    }

    public static void setNewLocale(Context c, String language) {
        persist(c, language);
        updateResources(c, language);
    }


    //private static final String SELECTED_LANGUAGE = "language_preference";

    /*public static Context onAttach(Context context) {
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        return setLocale(context, lang);
    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        return setLocale(context, lang);
    }*/

    public static String getLanguage(Context context) {
        return getPersistedData(context, DEFAULT_LANGUAGE);//Locale.getDefault().getLanguage()
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        /*if (Build.VERSION.SDK_INT >= 17) {//Build.VERSION_CODES.N
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        }else {*/
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        //}

        //save data to preference
        persist(context, language);

        return context;
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SettingsFragment.PREF_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SettingsFragment.PREF_LANGUAGE, language);
        editor.apply();
    }

    public static String rightLocale(String lang){
        if(lang.equals("tk")) {
            lang = "tm";
        }

        if(lang.equals("ka")) {
            lang = "ge";
        }

        return lang;
    }
}
