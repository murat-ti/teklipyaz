package com.android.teklipyaz.domains.network;

import com.android.teklipyaz.models.entities.CityResultModel;
import com.android.teklipyaz.models.entities.OrganizationCategoryResultModel;
import com.android.teklipyaz.models.entities.OrganizationResultModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("api/web/v1/city")
    Call<CityResultModel> getAllCity(
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("api/web/v1/orgcategory")
    Call<OrganizationCategoryResultModel> getAllCategory(
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("api/web/v1/organization")
    Call<OrganizationResultModel> getAllOrganization(
            @Query("language") String language,
            @Query("page") int pageIndex,
            @Query("city") int cityId,
            @Query("category") int categoryId,
            @Query("search") String query
    );
}
