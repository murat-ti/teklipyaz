package com.android.teklipyaz.views.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.teklipyaz.R;
import com.android.teklipyaz.utils.Utils;
import com.android.teklipyaz.views.fragments.AboutFragment;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.app_about_application));
        }

        Utils.switchFragmentWithAnimation(R.id.frag_container,
                new AboutFragment(), this, Utils.SETTINGS_FRAGMENT_TAG,
                Utils.AnimationType.SLIDE_UP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            closeActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        closeActivity();
    }

    private void closeActivity(){
        //super.onBackPressed();
        this.finish();
    }
}
