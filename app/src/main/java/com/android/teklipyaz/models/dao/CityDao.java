package com.android.teklipyaz.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.repositories.local.DBConstant;

import java.util.List;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<City> items);

    @Query("SELECT * FROM "+ DBConstant.CITY_TABLE)
    List<City> getAll();

    @Query("DELETE FROM "+ DBConstant.CITY_TABLE)
    void removeAll();
}
