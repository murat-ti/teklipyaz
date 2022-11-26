package com.android.teklipyaz.views.interfaces;

import com.android.teklipyaz.models.entities.OrganizationFav;

import java.util.List;

public interface PlaceDetailsViewInterface {
    void displayItem(boolean is_found);
    void displayError(Throwable t);
}
