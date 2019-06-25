package com.android.teklipyaz.views.fragments;

/**
 * Created by Lenovo on 13.10.2017.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.views.activities.MainActivity;

public class TextFragment extends Fragment {

    public TextFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_text, container, false);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (((MainActivity) getActivity()) != null) {
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setTitle(getString(R.string.app_about_application));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((MainActivity) getActivity());
        String languagePref = sharedPreferences.getString(MoreFragment.PREF_LANGUAGE, "");

        WebView webView = (WebView) view.findViewById(R.id.WebViewHtml);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadUrl("file:///android_asset/about-"+languagePref+".html");


        return view;
    }

}
