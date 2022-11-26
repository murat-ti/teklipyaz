package com.android.teklipyaz.models.repositories.local;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.android.teklipyaz.models.dao.CityDao;
import com.android.teklipyaz.models.entities.City;
import java.util.List;
import io.reactivex.Observable;

public class CitiesLocalRepoImpl implements CitiesLocalRepo{
    private CityDao itemsDao;


    public CitiesLocalRepoImpl(CityDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public Observable<List<City>> getAllLocal() {
        return Observable.fromCallable(() -> itemsDao.getAll());
    }

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void add(List<City> items) {
        itemsDao.insertAll(items);
    }

    /*@Query("UPDATE events_new SET name = :name WHERE id IS :id")
    void updateName(String name, int id);*/

    @Override
    public void removeAll() { itemsDao.removeAll(); }
}
