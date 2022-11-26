package com.android.teklipyaz.domains.network;

import com.android.teklipyaz.models.entities.CityResult;
import com.android.teklipyaz.models.entities.OrganizationCategoryResult;
import com.android.teklipyaz.models.entities.OrganizationResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterfaceTemp {

    @GET("api/web/v1/city")
    Call<CityResult> getAllCity(
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("api/web/v1/orgcategory")
    Call<OrganizationCategoryResult> getAllCategory(
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("api/web/v1/organization")
    Call<OrganizationResult> getAllOrganization(
            @Query("language") String language,
            @Query("page") int pageIndex,
            @Query("city") int cityId,
            @Query("category") int categoryId,
            @Query("search") String query
    );
}
