package com.android.teklipyaz.models.repositories.local;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.android.teklipyaz.models.dao.OrganizationCategoryDao;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import java.util.List;
import io.reactivex.Observable;

public class OrganizationCategoriesLocalRepoImpl implements OrganizationCategoriesLocalRepo {
    private OrganizationCategoryDao itemsDao;


    public OrganizationCategoriesLocalRepoImpl(OrganizationCategoryDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public Observable<List<OrganizationCategory>> getAllLocal() {
        return Observable.fromCallable(() -> itemsDao.getAll());
    }

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void add(List<OrganizationCategory> items) {
        itemsDao.insertAll(items);
    }

    /*@Query("UPDATE events_new SET name = :name WHERE id IS :id")
    void updateName(String name, int id);*/

    @Override
    public void removeAll() { itemsDao.removeAll(); }
}
