package com.android.teklipyaz.views.fragments;

/**
 * Created by Lenovo on 13.10.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.utils.Utils;
import com.android.teklipyaz.utils.VersionChecker;
import com.android.teklipyaz.views.activities.APrioriResultActivity;
import com.android.teklipyaz.views.activities.MainActivity;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MoreFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    public static final String PREF_LANGUAGE = "language_preference";
    public static final String PREF_VERSION = "version_preference";
    public static final String PREF_ABOUT = "about_preference";

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

        //language
        ListPreference languagePref = (ListPreference) findPreference(PREF_LANGUAGE);
        languagePref.setSummary(languagePref.getEntry().toString());

        //version
        String latestVersion = null;
        /*VersionChecker versionChecker = new VersionChecker();
        try {
            latestVersion = versionChecker.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        latestVersion = "1.1.1";

        String versionTxt = Utils.getVersion(getActivity().getApplicationContext());
        Preference versionPref = findPreference(PREF_VERSION);

        if( !latestVersion.equals(null) && !latestVersion.equals(versionTxt) ){
            versionTxt = versionTxt + "  ("+latestVersion+")";
        }

        versionPref.setSummary(versionTxt);

        //about
        Preference preference = findPreference(PREF_ABOUT);
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Utils.switchFragmentWithAnimation(R.id.frag_container,
                        new TextFragment(), ((MainActivity) (getContext())), Utils.PROFILE_FRAGMENT_TAG,
                        Utils.AnimationType.SLIDE_LEFT);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key)
    {
        /*if (key.equals(PREF_LANGUAGE)) {
            String langkey = sharedPreferences.getString(key, "");
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(PREF_LANGUAGE, langkey).commit();
            setLanguage(getActivity().getApplicationContext(), langkey);
            restartActivity();
        }*/
        //Toast.makeText(getContext(),"Else ishledi",Toast.LENGTH_LONG).show();
        switch (key){
            case PREF_LANGUAGE:
                String langkey = sharedPreferences.getString(key, "");
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(PREF_LANGUAGE, langkey).commit();
                setLanguage(getActivity().getApplicationContext(), langkey);
                restartActivity();
                break;
            //case PREF_ABOUT: Toast.makeText(getContext(),"About ishledi",Toast.LENGTH_LONG).show(); break;
        }
    }

    public static void setLanguage(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        //Toast.makeText(getContext(),"Set language recreate: "+locale.getLanguage(),Toast.LENGTH_LONG).show();
    }

    public void restartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

}
