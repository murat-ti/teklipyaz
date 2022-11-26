package com.android.teklipyaz.models.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyZip {

    private List<City> cities = null;
    public List<City> getCities() {
        return cities;
    }
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    private List<OrganizationCategory> organizationCategories = null;
    public List<OrganizationCategory> getOrganizationCategories() {
        return organizationCategories;
    }

    public void setOrganizationCategories(List<OrganizationCategory> organizationCategories) {
        this.organizationCategories = organizationCategories;
    }
}