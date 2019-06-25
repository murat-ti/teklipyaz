package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.base.remote.BaseRemote;
import com.databasefirst.base.remote.RemoteConfiguration;
import java.util.List;
import io.reactivex.Observable;

public class OrganizationCategoriesRemoteRepoImpl extends BaseRemote implements OrganizationCategoriesRemoteRepo {
    @Override
    public Observable<List<OrganizationCategory>> getAllOrganizationCategories() {
        return create(OrganizationCategoriesServices.class, RemoteConfiguration.BASE_URL).getAllCategory("ru",1);
    }

}
