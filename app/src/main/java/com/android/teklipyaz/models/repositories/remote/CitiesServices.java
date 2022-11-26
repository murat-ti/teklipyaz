package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.CityResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CitiesServices {

    @GET("api/web/{language}/v1/city")
    Observable<List<City>> getAll(
            @Path("language") String language,
            @Query("page") int pageIndex,
            @Query("access-token") String token
    );

    @GET("api/web/v1/orgcategory/updatedAt")
    Observable<CityResult> getAllUpdated(
            @Query("language") String language,
            @Query("update_at") String updatedAt
    );

}
