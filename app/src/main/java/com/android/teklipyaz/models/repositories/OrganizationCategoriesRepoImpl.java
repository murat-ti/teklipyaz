package com.android.teklipyaz.models.repositories;

import android.util.Log;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.repositories.local.OrganizationCategoriesLocalRepo;
import com.android.teklipyaz.models.repositories.remote.OrganizationCategoriesRemoteRepo;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrganizationCategoriesRepoImpl implements OrganizationCategoriesRepo {

    OrganizationCategoriesRemoteRepo remoteUsersRepo;
    OrganizationCategoriesLocalRepo localUsersRepo;


    public OrganizationCategoriesRepoImpl(OrganizationCategoriesRemoteRepo remoteUsersRepo, OrganizationCategoriesLocalRepo localUsersRepo) {
        this.remoteUsersRepo = remoteUsersRepo;
        this.localUsersRepo = localUsersRepo;
    }

    @Override
    public Observable<List<OrganizationCategory>> getAllOrganizationCategories() {

        /*return Observable.mergeDelayError(remoteUsersRepo.getAllOrganizationCategories()
                .doOnNext(new Consumer<List<OrganizationCategory>>() {
                    @Override
                    public void accept(List<OrganizationCategory> items) throws Exception {
                        localUsersRepo.addOrganizationCategories(items);
                        Log.d("OrgCatsRepoImpl","####################################################### Remote organization categories saved in local database");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        //do stuff with error message
                        Log.d("OrgCatsRepoImpl","####################################################### Error in receiving Remote organization categories");
                        //remoteUsersRepo.getAllOrganizationCategories().subscribeOn(Schedulers.io());
                    }
                })
                .subscribeOn(Schedulers.io()), localUsersRepo.getAllOrganizationCategories().subscribeOn(Schedulers.io())
        );*/
        Log.d("OrgCatsRepoImpl","####################################################### Select all local organization categories");
        return localUsersRepo.getAllOrganizationCategories().subscribeOn(Schedulers.io());

    }

}
