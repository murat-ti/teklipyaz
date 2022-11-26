package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.CityResult;

import java.util.List;

import io.reactivex.Observable;

public interface CitiesRemoteRepo {
    //Observable<CityResult> getAllRemote();
    Observable<List<City>> getAllRemote();
    Observable<CityResult> getAllUpdated(String updatedAt);

}
