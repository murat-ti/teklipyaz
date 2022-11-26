package com.android.teklipyaz.views.fragments;

/**
 * Created by Lenovo on 13.10.2017.
 */
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.teklipyaz.R;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.views.activities.SettingsActivity;

public class AboutFragment extends Fragment {

    private WebView webView;
    ProgressDialog prDialog;

    public AboutFragment() {
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

        /*final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (((SettingsActivity) getActivity()) != null) {
            ((SettingsActivity) getActivity()).setSupportActionBar(toolbar);
            ((SettingsActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((SettingsActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setTitle(getString(R.string.app_about_application));*/

        String currentLanguage = LocaleHelper.rightLocale(LocaleHelper.getLanguage(getActivity().getApplicationContext()));

        webView = (WebView) view.findViewById(R.id.WebViewHtml);
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadUrl("file:///android_asset/about-"+currentLanguage+".html");
        //webView.loadUrl("https://teklipyaz.com/ru/pages/o-proyekte");

        return view;
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            prDialog = new ProgressDialog(getActivity());
            prDialog.setMessage(getResources().getString(R.string.loading));
            prDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(prDialog!=null){
                prDialog.dismiss();
            }
        }
    }

}
