package com.android.teklipyaz.views.presenter;

import com.android.teklipyaz.base.view.MvpView;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import java.util.List;

public interface OrganizationCategoriesView extends MvpView {

    void showOrganizationCategories(List<OrganizationCategory> organizationCategories);
    void showLoading();
    void hideLoading();
    void showError(String error);
}
