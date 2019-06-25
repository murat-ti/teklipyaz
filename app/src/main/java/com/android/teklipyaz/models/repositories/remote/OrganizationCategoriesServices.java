package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationCategoryResultModel;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrganizationCategoriesServices {

    @GET("api/web/v1/orgcategory")
    Observable<List<OrganizationCategory>> getAllCategory(
            @Query("language") String language,
            @Query("page") int pageIndex
    );

}
