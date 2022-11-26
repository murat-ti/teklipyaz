package com.android.teklipyaz.views.interfaces;

import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationResult;

import java.util.List;

public interface PlaceCategoriesViewInterface {

    void showToast(String s);
    void displayItems(List<OrganizationCategory> categories);
    void displayError(Throwable t);
}
