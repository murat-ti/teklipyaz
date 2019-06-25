package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import java.util.List;
import io.reactivex.Observable;

public interface OrganizationCategoriesRemoteRepo {
    Observable<List<OrganizationCategory>> getAllOrganizationCategories();

}
