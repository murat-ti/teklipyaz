package com.android.teklipyaz.utils;

import android.content.Context;
import android.util.Log;

import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.ImageModel;
import com.android.teklipyaz.models.entities.Organization;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import com.android.teklipyaz.models.entities.OrganizationFav;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TypeConverter {

    private List<City> listCity;
    public TypeConverter(){

    }

    public static Organization favToOrg(OrganizationFav item){
        Organization org = new Organization();
        org.setId(item.getId());
        org.setCityId(item.getCityId());
        org.setCategoryId(item.getCategoryId());
        org.setTelephone(item.getTelephone());
        org.setEmail(item.getEmail());
        org.setWebsite(item.getWebsite());
        org.setWorkingTime(item.getWorkingTime());
        org.setLatitude(item.getLatitude());
        org.setLongitude(item.getLongitude());
        org.setHasBooking(item.getHasBooking());
        org.setCreatedTime(item.getCreatedTime());
        org.setUpdatedTime(item.getUpdatedTime());
        org.setTitle(item.getTitle());
        org.setDescription(item.getDescription());
        org.setAddress(item.getAddress());

        if(item.getImages() != null) {
            List<ImageModel> images = new ArrayList<>();
            ImageModel image = new ImageModel();
            image.setItemId("1");
            image.setFilepath(item.getImages());
            image.setIsMain(0);
            images.add(image);
            org.setImages(images);
        }

        return org;
    }

    public static OrganizationFav orgToFav(Organization item){
        OrganizationFav org = new OrganizationFav();
        org.setId(item.getId());
        org.setCityId(item.getCityId());
        org.setCategoryId(item.getCategoryId());
        org.setTelephone(item.getTelephone());
        org.setEmail(item.getEmail());
        org.setWebsite(item.getWebsite());
        org.setWorkingTime(item.getWorkingTime());
        org.setLatitude(item.getLatitude());
        org.setLongitude(item.getLongitude());
        org.setHasBooking(item.getHasBooking());
        org.setCreatedTime(item.getCreatedTime());
        org.setUpdatedTime(item.getUpdatedTime());
        org.setTitle(item.getTitle());
        org.setDescription(item.getDescription());
        org.setAddress(item.getAddress());

        if(item.getImages() != null && item.getImages().get(0) != null)
            org.setImages(item.getImages().get(0).getFilepath());

        return org;
    }
}
