/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.android.teklipyaz.models;

import com.android.teklipyaz.models.entities.CityModel;
import com.android.teklipyaz.models.entities.OrganizationModel;
import com.android.teklipyaz.models.entities.OrganizationCategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CenterRepository {

    private static CenterRepository centerRepository;

    private ArrayList<OrganizationCategory> listOfCategory = new ArrayList<OrganizationCategory>();
    private List<CityModel> listOfCity = new ArrayList<CityModel>();
    private List<OrganizationModel> listOfOrganizationModel = new ArrayList<OrganizationModel>();
    private ConcurrentHashMap<String, ArrayList<OrganizationModel>> mapOfOrganizationsInCategory = new ConcurrentHashMap<String, ArrayList<OrganizationModel>>();
    private List<OrganizationModel> listOfOrganizationsInShoppingList = Collections.synchronizedList(new ArrayList<OrganizationModel>());
    private List<Set<String>> listOfItemSetsForDataMining = new ArrayList<>();

    public static CenterRepository getCenterRepository() {

        if (null == centerRepository) {
            centerRepository = new CenterRepository();
        }
        return centerRepository;
    }


    public List<OrganizationModel> getListOfOrganizationsInShoppingList() {
        return listOfOrganizationsInShoppingList;
    }

    public void setListOfOrganizationsInShoppingList(ArrayList<OrganizationModel> getShoppingList) {
        this.listOfOrganizationsInShoppingList = getShoppingList;
    }

    public Map<String, ArrayList<OrganizationModel>> getMapOfOrganizationsInCategory() {

        return mapOfOrganizationsInCategory;
    }

    public void setMapOfOrganizationsInCategory(ConcurrentHashMap<String, ArrayList<OrganizationModel>> mapOfOrganizationsInCategory) {
        this.mapOfOrganizationsInCategory = mapOfOrganizationsInCategory;
    }

    public ArrayList<OrganizationCategory> getListOfCategory() {

        return listOfCategory;
    }

    public void setListOfCategory(ArrayList<OrganizationCategory> listOfCategory) {
        this.listOfCategory = listOfCategory;
    }

    public List<CityModel> getListOfCity() {

        return listOfCity;
    }

    public void setListOfCity(List<CityModel> listOfCity) {
        this.listOfCity = listOfCity;
    }

    public List<OrganizationModel> getListOfOrganization() {

        return listOfOrganizationModel;
    }

    public void setListOfOrganizationModel(List<OrganizationModel> listOfOrganizationModel) {
        this.listOfOrganizationModel = listOfOrganizationModel;
    }

    public List<Set<String>> getItemSetList() {

        return listOfItemSetsForDataMining;
    }

    public void addToItemSetList(HashSet list) {
        listOfItemSetsForDataMining.add(list);
    }

}
