package com.android.teklipyaz.views.fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.utils.Utils;
import com.android.teklipyaz.utils.VersionChecker;
import com.android.teklipyaz.views.activities.FragmentActivity;
import com.android.teklipyaz.views.activities.SettingsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PREF_COMMON = "header_common_preference";
    public static final String PREF_INFO = "header_info_preference";
    public static final String PREF_CITY = "city_preference";
    public static final String PREF_LANGUAGE = "language_preference";
    public static final String PREF_VERSION = "version_preference";
    public static final String PREF_ABOUT = "about_preference";
    public static final String PREF_EMAIL = "email_preference";

    private ListPreference cityPref;
    private List<City> cityListData = new ArrayList<City>();
    private String currentLanguage;
    private Context context;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        context = getActivity().getApplicationContext();

        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
        currentLanguage = LocaleHelper.rightLocale(LocaleHelper.getLanguage(context));

        //city
        getCity();

        //language
        getLanguage();

        //version
        getVersion();

        // email
        getEmail();

        //about
        getAbout();
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
        switch (key){
            case PREF_LANGUAGE:
                restartActivity();
                break;
            case PREF_CITY: cityPref.setSummary(cityPref.getEntry().toString()); break;
            //case PREF_ABOUT: Toast.makeText(getContext(),"About ishledi",Toast.LENGTH_LONG).show(); break;
        }
    }

    public void restartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    public void getCity(){
        cityPref = (ListPreference)findPreference(PREF_CITY);
        cityListData = CenterRepository.getCenterRepository().getCityList();
        List<String> entries = new ArrayList<String>();
        List<String> entryValues = new ArrayList<String>();

        if(cityListData != null && cityListData.size() > 0){
            if(cityListData != null) {
                for (int i = 0; i < cityListData.size(); i++) {
                    entries.add(cityListData.get(i).getTitle(LocaleHelper.rightLocale(currentLanguage)));
                    entryValues.add(cityListData.get(i).getId());
                }
            }
        }
        final CharSequence[] entryCharSeq = entries.toArray(new CharSequence[entries.size()]);
        final CharSequence[] entryValsChar = entryValues.toArray(new CharSequence[entryValues.size()]);

        cityPref.setEntries(entryCharSeq);
        cityPref.setEntryValues(entryValsChar);
        if(cityPref.getEntry() != null)
            cityPref.setSummary(cityPref.getEntry().toString());
    }

    public void getLanguage(){
        ListPreference languagePref = (ListPreference) findPreference(PREF_LANGUAGE);
        if(languagePref.getEntry() != null)
            languagePref.setSummary(languagePref.getEntry().toString());
    }

    public void getEmail(){
        //email
        Preference preferenceEmail = findPreference(PREF_EMAIL);
        preferenceEmail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(android.content.Intent.EXTRA_EMAIL, context.getResources().getString(R.string.pref_email));
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(i, "Send email"));
                return false;
            }
        });
    }

    public void getVersion(){
        String latestVersion = null;
        /*VersionChecker versionChecker = new VersionChecker();
        try {
            latestVersion = versionChecker.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        //latestVersion = "1.1.1";

        String versionTxt = Utils.getVersion(context);
        Preference versionPref = findPreference(PREF_VERSION);

        if( latestVersion != null && !latestVersion.equals(versionTxt) ){
            versionTxt = versionTxt + "  ("+latestVersion+")";
        }
        versionPref.setSummary(versionTxt);
    }

    public void getAbout(){
        Preference preferenceAbout = findPreference(PREF_ABOUT);
        preferenceAbout.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(context, FragmentActivity.class);
            startActivity(intent);
            return false;
        });
    }
}