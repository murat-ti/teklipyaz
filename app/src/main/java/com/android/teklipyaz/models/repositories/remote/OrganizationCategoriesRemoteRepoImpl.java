package com.android.teklipyaz.models.repositories.remote;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.base.remote.BaseRemote;
import com.android.teklipyaz.models.entities.OrganizationCategoryResult;
import com.android.teklipyaz.utils.LocaleHelper;
import com.databasefirst.base.remote.RemoteConfiguration;
import java.util.List;
import io.reactivex.Observable;

public class OrganizationCategoriesRemoteRepoImpl extends BaseRemote implements OrganizationCategoriesRemoteRepo {

    public String updatedAt;

    public OrganizationCategoriesRemoteRepoImpl(){
       this.updatedAt = "0000-00-00 00:00:00";
    }

    public OrganizationCategoriesRemoteRepoImpl(String updateAt){
        this.updatedAt = updateAt;
    }

    @Override
    public Observable<List<OrganizationCategory>> getAllRemote() {
        return create(OrganizationCategoriesServices.class, RemoteConfiguration.BASE_URL).getAll(LocaleHelper.DEFAULT_LANGUAGE,1, RemoteConfiguration.ACCESS_TOKEN);
    }

    @Override
    public Observable<OrganizationCategoryResult> getAllUpdated(String updatedAt) {
        return create(OrganizationCategoriesServices.class, RemoteConfiguration.BASE_URL).getAllUpdated(LocaleHelper.DEFAULT_LANGUAGE, updatedAt);
    }

}
