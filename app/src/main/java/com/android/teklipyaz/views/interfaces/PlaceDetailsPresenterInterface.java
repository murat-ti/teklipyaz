package com.android.teklipyaz.views.interfaces;

import com.android.teklipyaz.models.entities.OrganizationFav;

public interface PlaceDetailsPresenterInterface {
    void addItem(OrganizationFav item);
    void removeItem(OrganizationFav item);
    void getItem(String id);
}
