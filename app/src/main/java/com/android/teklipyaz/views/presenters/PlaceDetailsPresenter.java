package com.android.teklipyaz.views.presenters;

import android.util.Log;

import com.android.teklipyaz.models.dao.OrganizationFavDao;
import com.android.teklipyaz.models.entities.OrganizationFav;
import com.android.teklipyaz.models.repositories.local.OrganizationFavLocalRepo;
import com.android.teklipyaz.views.interfaces.PlaceDetailsPresenterInterface;
import com.android.teklipyaz.views.interfaces.PlaceDetailsViewInterface;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlaceDetailsPresenter implements PlaceDetailsPresenterInterface {

    PlaceDetailsViewInterface iplace;
    private String TAG = "PlaceDetailsPresenter";
    private OrganizationFavLocalRepo favLocalRepo;

    public PlaceDetailsPresenter(PlaceDetailsViewInterface iplace, OrganizationFavLocalRepo favLocalRepo) {
        this.iplace = iplace;
        this.favLocalRepo = favLocalRepo;
    }

    @Override
    public void addItem(OrganizationFav item) {
        favLocalRepo.add(item)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                    iplace.displayItem(true);
                }

                @Override
                public void onComplete() {
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    iplace.displayError(e);
                }
        });
    }

    @Override
    public void removeItem(OrganizationFav item) {
        favLocalRepo.remove(item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                iplace.displayItem(false);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                iplace.displayError(e);
            }
        });
    }

    @Override
    public void getItem(String id) {
        favLocalRepo.get(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(new MaybeObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onSuccess(Object o) {
                    //item found
                    iplace.displayItem(true);
                }

                @Override
                public void onComplete() {
                    //item not found
                    iplace.displayItem(false);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    iplace.displayError(e);
                }
        });
    }
}
