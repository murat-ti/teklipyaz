package com.android.teklipyaz.models.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationFav;
import com.android.teklipyaz.models.repositories.local.DBConstant;

@Database(entities = {OrganizationCategory.class, City.class, OrganizationFav.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OrganizationCategoryDao organizationCategoryDao();
    public abstract OrganizationFavDao organizationFavDao();
    public abstract CityDao cityDao();

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DBConstant.DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }
}