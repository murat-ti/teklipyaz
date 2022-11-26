package com.android.teklipyaz.views.presenters;

import android.util.Log;

import com.android.teklipyaz.domains.network.NetworkClient;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationResult;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepo;
import com.android.teklipyaz.models.repositories.remote.PlacesInterface;
import com.android.teklipyaz.views.interfaces.PlaceCategoriesPresenterInterface;
import com.android.teklipyaz.views.interfaces.PlaceCategoriesViewInterface;
import com.android.teklipyaz.views.interfaces.PlacePresenterInterface;
import com.android.teklipyaz.views.interfaces.PlaceCategoriesViewInterface;
import com.databasefirst.base.remote.RemoteConfiguration;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlaceCategoriesPresenter implements PlaceCategoriesPresenterInterface{

    PlaceCategoriesViewInterface ilist;
    private String TAG = "PlaceCategoriesPresenterInterface";
    OrganizationCategoriesLocalRepo localItemsRepo;

    public PlaceCategoriesPresenter(PlaceCategoriesViewInterface ilist, OrganizationCategoriesLocalRepo localItemsRepo) {
        this.ilist = ilist;
        this.localItemsRepo = localItemsRepo;
    }

    @Override
    public void getItems(String language) {
        getObservable(language).subscribeWith(getObserver());
    }

    public Observable<List<OrganizationCategory>> getObservable(String language){
        return localItemsRepo.getAllLocal().subscribeOn(Schedulers.io());
    }

    public DisposableObserver<List<OrganizationCategory>> getObserver(){
        return new DisposableObserver<List<OrganizationCategory>>() {

            @Override
            public void onNext(@NonNull List<OrganizationCategory> results) {
                ilist.displayItems(results);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                ilist.displayError(e);
            }

            @Override
            public void onComplete() {
            }
        };
    }
}
