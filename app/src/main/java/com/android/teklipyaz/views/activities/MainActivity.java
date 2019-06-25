package com.android.teklipyaz.views.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.views.fragments.CityFragment;
import com.android.teklipyaz.utils.Utils;
import com.android.teklipyaz.views.fragments.MoreFragment;
import com.android.teklipyaz.views.fragments.OrganizationListFragment;
import com.wang.avi.AVLoadingIndicatorView;

public class MainActivity extends AppCompatActivity{

    SharedPreferences sharedPreferences;
    private AVLoadingIndicatorView progressBar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languagePref = sharedPreferences.getString(MoreFragment.PREF_LANGUAGE, "");
        MoreFragment.setLanguage(this.getBaseContext(),languagePref);
        setContentView(R.layout.activity_main);

        //progressBar = (AVLoadingIndicatorView) findViewById(R.id.loading_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);

        Utils.switchFragmentWithAnimation(R.id.frag_container,
                new OrganizationListFragment(), this, Utils.HOME_FRAGMENT_TAG,
                Utils.AnimationType.SLIDE_UP);


    }

    public AVLoadingIndicatorView getProgressBar() {
        return progressBar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else {
                    super.onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            Toast.makeText(this,"Back button clicked",Toast.LENGTH_LONG).show();
            fm.popBackStack();
        } else {
            finish();
            super.onBackPressed();
        }
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }
}
