package com.android.teklipyaz.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class IconTextView extends TextView {

    private Context context;

    public IconTextView(Context context) {
        super(context);
        this.context = context;
        createView();
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    private void createView(){
        setGravity(Gravity.CENTER);
        /*Typeface type = Typeface.createFromAsset(context.getAssets(),"font/Font Awesome 5 Free-Regular-400.otf");
        setTypeface(type);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"font/Font Awesome 5 Brands-Regular-400.otf");
        setTypeface(type);*/
        Typeface type = Typeface.createFromAsset(context.getAssets(), "font/Font Awesome 5 Free-Solid-900.otf");
        setTypeface(type);
        //setTypeface(FontTypeface.get("Font Awesome 5 Brands-Regular-400.otf", context));
    }
}