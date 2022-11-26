package com.android.teklipyaz.models.repositories.remote;
import com.android.teklipyaz.models.entities.OrganizationResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesInterface {

    @GET("api/web/v1/organization")
    Observable<OrganizationResult> getPlaces(
            @Query("language") String language,
            @Query("page") int pageIndex,
            @Query("city") int cityId,
            @Query("category") int categoryId,
            @Query("search") String query
    );
}
