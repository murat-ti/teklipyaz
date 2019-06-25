package com.android.teklipyaz.models.repositories.local;

import com.android.teklipyaz.models.dao.OrganizationCategoryDao;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class OrganizationCategoriesLocalRepoImpl implements OrganizationCategoriesLocalRepo {
    private OrganizationCategoryDao itemsDao;


    public OrganizationCategoriesLocalRepoImpl(OrganizationCategoryDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public Observable<List<OrganizationCategory>> getAllOrganizationCategories() {
        return Observable.fromCallable(new Callable<List<OrganizationCategory>>() {
            @Override
            public List<OrganizationCategory> call() throws Exception {
                return itemsDao.getAll();
            }
        });
    }

    @Override
    public void addOrganizationCategories(List<OrganizationCategory> items) {
        itemsDao.insertAll(items);
    }
}
