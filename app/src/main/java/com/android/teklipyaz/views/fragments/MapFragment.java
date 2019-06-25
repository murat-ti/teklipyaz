package com.android.teklipyaz.views.fragments;

/**
 * Created by Lenovo on 13.10.2017.
 */

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.views.activities.MainActivity;

public class MapFragment extends Fragment {

    public MapFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*View view = inflater.inflate(R.layout.frag_text, container, false);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (((MainActivity) getActivity()) != null) {
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setTitle(getString(R.string.app_about_application));

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((MainActivity) getActivity());
        //String languagePref = sharedPreferences.getString(MoreFragment.PREF_LANGUAGE, "");
        final ProgressDialog pd = ProgressDialog.show(((MainActivity) getActivity()), "", "Загрузка teklipyaz.com",true);
        final WebView webView = (WebView) view.findViewById(R.id.WebViewHtml);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                //super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url){
                pd.dismiss();
                String webUrl = webView.getUrl();
            }
        });

        webView.loadUrl("http://192.168.43.83/teklipyaz/map");*/
        View view = inflater.inflate(R.layout.frag_event_list, container, false);

        return view;
    }

}
