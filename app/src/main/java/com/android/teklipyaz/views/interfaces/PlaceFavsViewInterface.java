package com.android.teklipyaz.views.interfaces;

import com.android.teklipyaz.models.entities.OrganizationFav;
import java.util.List;

public interface PlaceFavsViewInterface {
    void showToast(String s);
    void displayItems(List<OrganizationFav> items);
    void displayError(Throwable t);
}
