package com.android.teklipyaz.models.repositories.local;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import java.util.List;
import io.reactivex.Observable;


public interface OrganizationCategoriesLocalRepo {
    Observable<List<OrganizationCategory>> getAllOrganizationCategories();
    void addOrganizationCategories(List<OrganizationCategory> organizationCategories);

}
