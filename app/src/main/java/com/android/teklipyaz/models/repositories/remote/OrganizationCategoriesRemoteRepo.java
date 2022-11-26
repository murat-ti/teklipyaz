package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationCategoryResult;
import java.util.List;
import io.reactivex.Observable;

public interface OrganizationCategoriesRemoteRepo {
    //Observable<OrganizationCategoryResult> getAllRemote();
    Observable<List<OrganizationCategory>> getAllRemote();
    Observable<OrganizationCategoryResult> getAllUpdated(String updatedAt);

}
