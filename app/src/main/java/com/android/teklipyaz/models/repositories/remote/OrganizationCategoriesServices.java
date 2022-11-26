package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationCategoryResult;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrganizationCategoriesServices {

    @GET("api/web/{language}/v1/orgcategory")
    //@GET("api/web/v1/orgcategory")
    //Observable<OrganizationCategoryResult> getAll(
    Observable<List<OrganizationCategory>> getAll(
    // getAll(
            @Path("language") String language,
            @Query("page") int pageIndex,
            @Query("access-token") String token
    );

    @GET("api/web/v1/orgcategory/updatedAt")
    Observable<OrganizationCategoryResult> getAllUpdated(
            @Query("language") String language,
            @Query("update_at") String updatedAt
    );

}
