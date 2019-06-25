package com.android.teklipyaz.models.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.teklipyaz.models.entities.OrganizationCategory;

@Database(entities = {OrganizationCategory.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OrganizationCategoryDao organizationCategoryDao();

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "data_db")
                            .build();
                }
            }
        }
        return instance;
    }
}