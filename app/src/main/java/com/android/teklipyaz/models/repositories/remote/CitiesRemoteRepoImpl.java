package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.base.remote.BaseRemote;
import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.CityResult;
import com.android.teklipyaz.utils.LocaleHelper;
import com.databasefirst.base.remote.RemoteConfiguration;

import java.util.List;

import io.reactivex.Observable;

public class CitiesRemoteRepoImpl extends BaseRemote implements CitiesRemoteRepo {

    public String updatedAt;

    public CitiesRemoteRepoImpl(){
       this.updatedAt = "0000-00-00 00:00:00";
    }

    public CitiesRemoteRepoImpl(String updateAt){
        this.updatedAt = updateAt;
    }

    @Override
    public Observable<List<City>> getAllRemote() {
        return create(CitiesServices.class, RemoteConfiguration.BASE_URL).getAll(LocaleHelper.DEFAULT_LANGUAGE,1, RemoteConfiguration.ACCESS_TOKEN);
    }

    @Override
    public Observable<CityResult> getAllUpdated(String updatedAt) {
        return create(CitiesServices.class, RemoteConfiguration.BASE_URL).getAllUpdated(LocaleHelper.DEFAULT_LANGUAGE, updatedAt);
    }

}
