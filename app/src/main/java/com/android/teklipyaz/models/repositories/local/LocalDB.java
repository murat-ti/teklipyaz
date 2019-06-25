package com.android.teklipyaz.models.repositories.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.android.teklipyaz.models.dao.OrganizationCategoryDao;
import com.android.teklipyaz.models.entities.OrganizationCategory;

@Database(entities = {OrganizationCategory.class}, version = 1, exportSchema = false)
public abstract class LocalDB extends RoomDatabase {

    public abstract OrganizationCategoryDao categoriesDao();
}
