package com.android.teklipyaz.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.repositories.local.DBConstant;
import java.util.List;

@Dao
public interface OrganizationCategoryDao {
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(OrganizationCategory ocategory);

    @Update
    abstract void update(OrganizationCategory ocategory);

    @Delete
    abstract void delete(OrganizationCategory ocategory);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OrganizationCategory> items);

    @Query("SELECT * FROM "+ DBConstant.ORGANIZATION_CATEGOTY_TABLE)
    List<OrganizationCategory> getAll();

    @Query("DELETE FROM "+ DBConstant.ORGANIZATION_CATEGOTY_TABLE)
    void removeAll();
}
