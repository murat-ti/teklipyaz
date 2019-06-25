package com.android.teklipyaz.utils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ErrorView {
    private LinearLayout errorLayout;
    private ProgressBar progressBar;

    public ErrorView (LinearLayout errorLayout, ProgressBar progressBar) {
        this.errorLayout = errorLayout;
        this.progressBar = progressBar;
    }

    public void show() {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    // Helpers -------------------------------------------------------------------------------------
    public void hide() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
