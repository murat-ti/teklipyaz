package com.android.teklipyaz.utils;

import android.content.Context;
import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.entities.City;
import com.android.teklipyaz.models.entities.OrganizationCategory;
import java.util.List;

public class ListUtil {

    private List<OrganizationCategory> listOrganizationCategory;
    private List<City> listCity;
    private String language;
    private Context context;

    public ListUtil(Context context){
        this.context = context;
        this.language = LocaleHelper.getLanguage(context);
        this.listOrganizationCategory = CenterRepository.getCenterRepository().getOrganizationCategoryList();
        this.listCity = CenterRepository.getCenterRepository().getCityList();
    }

    public String getOrganizationCategoryItem(String id){
        String title = id;
        if(listOrganizationCategory != null) {
            for (int i = 0; i < listOrganizationCategory.size(); i++) {
                if (Integer.parseInt(listOrganizationCategory.get(i).getId()) == Integer.parseInt(id)) {
                    title = listOrganizationCategory.get(i).getTitle(LocaleHelper.rightLocale(language));
                    break;
                }
            }
        }
        return title;
    }

    public String getCityItem(String id){
        String title = id;
        if(listCity != null) {
            for (int i = 0; i < listCity.size(); i++) {
                if (Integer.parseInt(listCity.get(i).getId()) == Integer.parseInt(id)) {
                    title = listCity.get(i).getTitle(LocaleHelper.rightLocale(language));
                    break;
                }
            }
        }
        return title;
    }
}
