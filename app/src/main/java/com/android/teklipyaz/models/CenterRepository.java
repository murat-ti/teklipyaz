/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.android.teklipyaz.models;

import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.Organization;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationFav;

import java.util.ArrayList;
import java.util.List;

public class CenterRepository {

    private static CenterRepository centerRepository;

    private List<Organization> organizationList = new ArrayList<Organization>();
    private List<OrganizationCategory> organizationCategoryList = new ArrayList<OrganizationCategory>();
    private List<City> cityList = new ArrayList<City>();
    private Organization organization = new Organization();
    //private OrganizationFav organizationFav = new OrganizationFav();

    public static CenterRepository getCenterRepository() {

        if (null == centerRepository) {
            centerRepository = new CenterRepository();
        }
        return centerRepository;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public List<OrganizationCategory> getOrganizationCategoryList() {
        return organizationCategoryList;
    }

    public void setOrganizationCategoryList(List<OrganizationCategory> organizationCategoryList) {
        this.organizationCategoryList = organizationCategoryList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /*public OrganizationFav getOrganizationFav() {
        return organizationFav;
    }

    public void setOrganizationFav(OrganizationFav organizationFav) {
        this.organizationFav = organizationFav;
    }*/

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
