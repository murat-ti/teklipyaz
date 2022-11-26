package com.android.teklipyaz.views.presenters;

import android.util.Log;

import com.android.teklipyaz.domains.network.NetworkClient;
import com.android.teklipyaz.models.entities.OrganizationResult;
import com.android.teklipyaz.models.repositories.remote.PlacesInterface;
import com.android.teklipyaz.views.interfaces.PlacePresenterInterface;
import com.android.teklipyaz.views.interfaces.PlaceViewInterface;
import com.databasefirst.base.remote.RemoteConfiguration;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlacePresenter implements PlacePresenterInterface {

    PlaceViewInterface iplace;
    private String TAG = "PlacePresenter";


    public PlacePresenter(PlaceViewInterface iplace) {
        this.iplace = iplace;
    }

    @Override
    public void getItems(String language, int pageIndex, int cityId, int categoryId, String query) {
        getObservable(language, pageIndex, cityId, categoryId, query).subscribeWith(getObserver());
    }

    public Observable<OrganizationResult> getObservable(String language, int pageIndex, int cityId, int categoryId, String query){
        return NetworkClient.getRetrofit(RemoteConfiguration.BASE_URL)
                .create(PlacesInterface.class)
                .getPlaces(language, pageIndex, cityId, categoryId, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<OrganizationResult> getObserver(){
        return new DisposableObserver<OrganizationResult>() {

            @Override
            public void onNext(@NonNull OrganizationResult results) {
                iplace.displayItems(results);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                iplace.displayError(e);
            }

            @Override
            public void onComplete() {
            }
        };
    }
}
