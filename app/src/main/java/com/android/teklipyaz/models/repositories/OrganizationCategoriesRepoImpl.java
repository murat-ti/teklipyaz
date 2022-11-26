package com.android.teklipyaz.models.repositories;

import android.util.Log;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepo;
import com.android.teklipyaz.models.repositories.remote.OrganizationCategoriesRemoteRepo;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class OrganizationCategoriesRepoImpl implements OrganizationCategoriesRepo {

    OrganizationCategoriesRemoteRepo remoteItemsRepo;
    OrganizationCategoriesLocalRepo localItemsRepo;


    public OrganizationCategoriesRepoImpl(OrganizationCategoriesRemoteRepo remoteItemsRepo, OrganizationCategoriesLocalRepo localItemsRepo) {
        this.remoteItemsRepo = remoteItemsRepo;
        this.localItemsRepo = localItemsRepo;
    }

    @Override
    public Observable<List<OrganizationCategory>> getAllOrganizationCategories() {
        /*return Flowable.mergeDelayError(remoteItemsRepo.getAllRemote()
                .doOnNext(new Consumer<List<OrganizationCategory>>() {
                    @Override
                    public void accept(List<OrganizationCategory> items) throws Exception {
                        Log.d("MYRESPONSE","####################################################### Data saved in database ");
                        localItemsRepo.add(items);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        //do stuff with error message
                        Log.d("MYRESPONSE","####################################################### Error in receiving Remote organization categories: "+throwable.getMessage());
                        //remoteItemsRepo.getAllOrganizationCategories().subscribeOn(Schedulers.io());
                    }
                })
                .subscribeOn(Schedulers.io()), localItemsRepo.getAllLocal().subscribeOn(Schedulers.io())
        );*///.observeOn(AndroidSchedulers.mainThread(), true) , localItemsRepo...

        /*return remoteItemsRepo.getAllRemote().subscribeOn(Schedulers.io()).onErrorResumeNext(
                localItemsRepo.getAllLocal().subscribeOn(Schedulers.io()));*/

        /*return Flowable.mergeDelayError(remoteItemsRepo.getAllRemote().doOnNext(new Consumer<List<OrganizationCategory>>() {
                    @Override
                    public void accept(List<OrganizationCategory> items) throws Exception {
                        localItemsRepo.add(items);
                    }
                }).subscribeOn(Schedulers.io()), localItemsRepo.getAllLocal().subscribeOn(Schedulers.io())
        );*/
        //return remoteItemsRepo.getAllRemote().subscribeOn(Schedulers.io());
        return localItemsRepo.getAllLocal().subscribeOn(Schedulers.io());

    }

}
