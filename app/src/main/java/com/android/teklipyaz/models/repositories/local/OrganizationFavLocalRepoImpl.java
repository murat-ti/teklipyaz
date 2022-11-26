package com.android.teklipyaz.models.repositories.local;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.util.Log;

import com.android.teklipyaz.models.dao.OrganizationFavDao;
import com.android.teklipyaz.models.entities.OrganizationFav;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class OrganizationFavLocalRepoImpl implements OrganizationFavLocalRepo {
    private OrganizationFavDao itemsDao;


    public OrganizationFavLocalRepoImpl(OrganizationFavDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public Observable<List<OrganizationFav>> getAllLocal() {
        return Observable.fromCallable(() -> itemsDao.getAll());
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable add(OrganizationFav item) {
        return Completable.fromAction(() -> itemsDao.insert(item));
    }

    @Override
    public Maybe<OrganizationFav> get(String id) {
        return Maybe.fromCallable(() -> itemsDao.get(id));
    }

    @Delete
    public Completable remove(OrganizationFav item) {
        return Completable.fromAction(() -> itemsDao.delete(item));
    }

    /*@Override
    public void removeAll() { itemsDao.removeAll(); }*/
}
