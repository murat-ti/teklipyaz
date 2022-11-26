/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.android.teklipyaz.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.dao.AppDatabase;
import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.MyZip;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.repositories.local.CitiesLocalRepo;
import com.android.teklipyaz.models.repositories.local.CitiesLocalRepoImpl;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepo;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepoImpl;
import com.android.teklipyaz.models.repositories.remote.CitiesRemoteRepo;
import com.android.teklipyaz.models.repositories.remote.CitiesRemoteRepoImpl;
import com.android.teklipyaz.models.repositories.remote.OrganizationCategoriesRemoteRepo;
import com.android.teklipyaz.models.repositories.remote.OrganizationCategoriesRemoteRepoImpl;
import com.android.teklipyaz.utils.AppConstants;
import com.android.teklipyaz.utils.ErrorView;
import com.android.teklipyaz.utils.LocaleHelper;
import com.android.teklipyaz.utils.Network;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;


public class SplashActivity extends FragmentActivity {

    public String TAG = "Splash3";
    private Animation animation;
    private ImageView logo;
    private TextView appTitle;
    private TextView appSlogan;
    private String organization_category_date = "0000-00-00 00:00:00";

    private CitiesRemoteRepo remoteRepoCities;
    private OrganizationCategoriesRemoteRepo remoteRepoOrganizationCategories;

    private Observable<List<City>> citiesList;
    private Observable<List<OrganizationCategory>> orgCategoriesList;

    OrganizationCategoriesLocalRepo localItemsRepoOrganizationCategories;
    CitiesLocalRepo localItemsRepoCities;

    private AppDatabase localDB;
    private Disposable disposable;
    private Button btnRetry;
    ProgressBar progressBar;
    LinearLayout errorLayout;
    TextView  errorTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ErrorView errorView;
    private Network network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDB = AppDatabase.getDatabase(getApplicationContext());
        localItemsRepoOrganizationCategories = new OrganizationCategoriesLocalRepoImpl(localDB.organizationCategoryDao());
        localItemsRepoCities = new CitiesLocalRepoImpl(localDB.cityDao());

        configureLayout();
        createObservable();


        //new Handler().postDelayed(() -> startMyActivity(), 2000);
    }

    private void configureLayout() {
        setContentView(R.layout.activity_splash);
        LocaleHelper.setLocale(SplashActivity.this);
        //internet connection
        network = new Network(this);

        logo = (ImageView) findViewById(R.id.logo_img);
        appTitle = (TextView) findViewById(R.id.track_txt);
        appSlogan = (TextView) findViewById(R.id.pro_txt);

        //error view
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        errorView = new ErrorView(errorLayout, progressBar);
        errorTitle = (TextView) findViewById(R.id.error_txt_cause);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);

        btnRetry.setOnClickListener(view -> createObservable());
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            //Toast.makeText(this, "Swipe", Toast.LENGTH_SHORT).show();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
                createObservable();
            }
        });
    }

    private void createObservable() {
        //cities
        remoteRepoCities = new CitiesRemoteRepoImpl();
        citiesList = remoteRepoCities.getAllRemote();

        //organization categories
        remoteRepoOrganizationCategories = new OrganizationCategoriesRemoteRepoImpl();
        orgCategoriesList = remoteRepoOrganizationCategories.getAllRemote();

        errorView.hide();

        //disposable = orgCategoriesList
        disposable = Observable.zip(citiesList,orgCategoriesList, (cities, organizationCategories) -> {
                    MyZip myzip = new MyZip();
                    myzip.setCities(cities);
                    myzip.setOrganizationCategories(organizationCategories);
                    return myzip;
                })
                .timeout(AppConstants.TIMEOUT_TIME, TimeUnit.SECONDS)
                .delay(AppConstants.DELAY_TIME, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MyZip>() {
                    @Override
                    public void onNext(MyZip myZip) {
                        //removeAllFromDb();
                        addToDbCities(myZip.getCities());
                        addToDbOrganizationCategories(myZip.getOrganizationCategories());
                    }

                    @Override
                    public void onError(Throwable t) {
                        errorView.show();
                        errorTitle.setText(network.fetchErrorMessage(t));
                        //errorTitle.setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addToDbCities(List<City> items) {
        Completable.fromAction(() -> { localItemsRepoCities.add(items); })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onComplete() {
                    CenterRepository.getCenterRepository().setCityList(items);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(SplashActivity.this, "Database access problem 1", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void addToDbOrganizationCategories(List<OrganizationCategory> items) {
        Completable.fromAction(() -> { localItemsRepoOrganizationCategories.add(items); })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                CenterRepository.getCenterRepository().setOrganizationCategoryList(items);
                startMyActivity();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SplashActivity.this, "Database access problem 2", Toast.LENGTH_LONG).show();
                //Log.d("DBerror",e.getMessage());
            }
        });
    }

    private void startMyActivity(){
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        finish();
    }

    /*private void removeAllFromDb() {
        localItemsRepoOrganizationCategories.removeAll();
    }*/

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
