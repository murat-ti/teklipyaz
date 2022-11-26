package com.android.teklipyaz.views.presenters;

import android.util.Log;

import com.android.teklipyaz.models.dao.OrganizationFavDao;
import com.android.teklipyaz.models.entities.OrganizationFav;
import com.android.teklipyaz.models.repositories.local.OrganizationFavLocalRepo;
import com.android.teklipyaz.views.interfaces.PlaceFavsPresenterInterface;
import com.android.teklipyaz.views.interfaces.PlaceFavsViewInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlaceFavsPresenter implements PlaceFavsPresenterInterface {

    PlaceFavsViewInterface ilist;
    private String TAG = "PlaceFavsPresenter";
    private OrganizationFavLocalRepo localItemsRepo;

    public PlaceFavsPresenter(PlaceFavsViewInterface ilist, OrganizationFavLocalRepo localItemsRepo) {
        this.ilist = ilist;
        this.localItemsRepo = localItemsRepo;
    }

    @Override
    public void getItems() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<List<OrganizationFav>> getObservable(){
        return localItemsRepo.getAllLocal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<List<OrganizationFav>> getObserver(){
        return new DisposableObserver<List<OrganizationFav>>() {

            @Override
            public void onNext(@NonNull List<OrganizationFav> results) {
                ilist.displayItems(results);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                ilist.displayError(e);
            }

            @Override
            public void onComplete() {
            }
        };
    }
}
