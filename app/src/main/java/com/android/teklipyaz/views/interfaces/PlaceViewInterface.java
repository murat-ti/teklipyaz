package com.android.teklipyaz.views.interfaces;

import com.android.teklipyaz.models.entities.OrganizationResult;

public interface PlaceViewInterface {

    void showToast(String s);
    void displayItems(OrganizationResult organizationResult);
    void displayError(Throwable t);
}
