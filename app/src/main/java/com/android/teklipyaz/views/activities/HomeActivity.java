/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.android.teklipyaz.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.AppCompatImageView;
import android.widget.Toast;

import com.android.teklipyaz.R;


public class HomeActivity extends AppCompatActivity {

    Button mPlaceBtn, mEventBtn, mBlogBtn, mCatalogBtn;
    /**
     * The double back to exit pressed once.
     */
    boolean doubleBackToExitPressedOnce = false;
    private AppCompatImageView bm_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPlaceBtn = (Button)findViewById(R.id.btnPlace);
        mEventBtn = (Button)findViewById(R.id.btnEvent);
        mBlogBtn = (Button)findViewById(R.id.btnBlog);
        mCatalogBtn = (Button)findViewById(R.id.btnCatalog);

        //settings
        bm_settings = (AppCompatImageView) findViewById(R.id.bottom_menu_settings);
        //settings page
        bm_settings.setOnClickListener((View v) -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            finish();
            startActivity(intent);
        });

        mPlaceBtn.setOnClickListener(v -> {
            //Toast.makeText(HomeActivity.this,"Place clicked",Toast.LENGTH_SHORT).show();
            startMyActivity("place");
        });

        mEventBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this,"Event clicked",Toast.LENGTH_SHORT).show();
            //startMyActivity("place");
        });

        mBlogBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this,"Blog clicked",Toast.LENGTH_SHORT).show();
            //startMyActivity("place");
        });

        mCatalogBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this,"Catalog clicked",Toast.LENGTH_SHORT).show();
            //startMyActivity("place");
        });
    }

    private void startMyActivity(String page_id){
        Intent i = null;
        switch (page_id){
            case "place": i = new Intent(getApplicationContext(), PlaceActivity.class); break;
            case "event": i = new Intent(getApplicationContext(), PlaceActivity.class); break;
            case "blog": i = new Intent(getApplicationContext(), PlaceActivity.class); break;
            case "catalog": i = new Intent(getApplicationContext(), PlaceActivity.class); break;
            default: page_id = null; break;
        }

        if (!TextUtils.isEmpty(page_id) && i != null) {
            startActivity(i);
            //finish();
        }

    }

    /*private void removeAllFromDb() {
        localItemsRepoOrganizationCategories.removeAll();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.double_click_to_exit), Toast.LENGTH_SHORT).show();

        //new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }
}
