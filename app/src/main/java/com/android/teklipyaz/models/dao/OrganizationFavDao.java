package com.android.teklipyaz.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.android.teklipyaz.models.entities.OrganizationFav;
import com.android.teklipyaz.models.repositories.local.DBConstant;
import java.util.List;

@Dao
public interface OrganizationFavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrganizationFav item);

    @Delete
    void delete(OrganizationFav item);

    @Query("SELECT * FROM "+ DBConstant.ORGANIZATION_FAV_TABLE)
    List<OrganizationFav> getAll();

    /*@Query("DELETE FROM "+ DBConstant.ORGANIZATION_FAV_TABLE+" WHERE id = :id")
    void remove(String id);*/

    @Query("SELECT * FROM "+DBConstant.ORGANIZATION_FAV_TABLE+" WHERE id = :id LIMIT 1")
    OrganizationFav get(String id);
}
