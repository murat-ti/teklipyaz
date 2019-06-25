package com.android.teklipyaz.models.repositories;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import java.util.List;
import io.reactivex.Observable;

public interface OrganizationCategoriesRepo {
    Observable<List<OrganizationCategory>> getAllOrganizationCategories();

}
