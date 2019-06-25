package com.android.teklipyaz.domains.mock;

import android.util.Log;

import com.android.teklipyaz.models.CenterRepository;
import com.android.teklipyaz.models.entities.CityModel;
import com.android.teklipyaz.models.entities.OrganizationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * This class serve as fake server and provides dummy organization and category with real ImageHelper Urls taken from flipkart
 */
public class FakeWebServerCity {

    private static FakeWebServerCity fakeServer;
    private static final String API_SERVER_URL ="https://teklipyaz.com/";
    private static final String API_SERVER_PATH =API_SERVER_URL+"api/web/ru/city";
    private static final String API_IMAGE_PATH =API_SERVER_URL+"images/store/";

    public static FakeWebServerCity getFakeWebServer() {

        if (null == fakeServer) {
            fakeServer = new FakeWebServerCity();
        }
        return fakeServer;
    }

    /*void initiateFakeServer() {

        addCity();
    }*/

    public void addCity() {
        String response = "[{\"id\":\"1\",\"title_tm\":\"Aşgabat\",\"title_ru\":\"Ашгабат\",\"title_en\":\"Ashgabat\",\"slug\":\"ashgabat\",\"status\":\"1\",\"image\":\"Citys/City1/3d2851.jpg\"},{\"id\":\"2\",\"title_tm\":\"Balkan\",\"title_ru\":\"Балкан\",\"title_en\":\"Balkan\",\"slug\":\"balkan\",\"status\":\"1\",\"image\":\"Citys/City2/7863ac.jpg\"},{\"id\":\"3\",\"title_tm\":\"Mary\",\"title_ru\":\"Мары\",\"title_en\":\"Mary\",\"slug\":\"mary\",\"status\":\"1\",\"image\":\"Citys/City3/9c643b.jpg\"},{\"id\":\"4\",\"title_tm\":\"Ahal\",\"title_ru\":\"Ахал\",\"title_en\":\"Ahal\",\"slug\":\"ahal\",\"status\":\"1\",\"image\":\"Citys/City4/ff238c.jpg\"},{\"id\":\"5\",\"title_tm\":\"Lebap\",\"title_ru\":\"Лебап\",\"title_en\":\"Lebap\",\"slug\":\"lebap\",\"status\":\"1\",\"image\":\"Citys/City5/cc687a.jpg\"},{\"id\":\"6\",\"title_tm\":\"Daşoguz\",\"title_ru\":\"Дашогуз\",\"title_en\":\"Dashoguz\",\"slug\":\"dashoguz\",\"status\":\"1\",\"image\":\"Citys/City6/1605e3.jpg\"}]";
        ArrayList<CityModel> ListOfCity = new ArrayList<CityModel>();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        List<CityModel> cityList = new ArrayList<CityModel>();
        cityList = Arrays.asList(gson.fromJson(response, CityModel[].class));

        /*for (int i = 0; i < cityList.size(); i++) {
            Log.d("TAG", "ImageHelper: "+API_IMAGE_PATH+cityList.get(i).getImage());
            ListOfCity.add(new CityModel(
                    cityList.get(i).getTitleTm(),
                    cityList.get(i).getTitleRu(),
                    cityList.get(i).getTitleEn(),
                    cityList.get(i).getSlug(),
                    cityList.get(i).getStatus(),
                    //"https://teklipyaz.com/images/store/Citys/City1/3d2851.jpg",
                    API_IMAGE_PATH+cityList.get(i).getImage(),
                    cityList.get(i).getId()
            ));
        }*/

        /*for(int i = 1; i<=25; i++) {
            ListOfCity
                    .add(new CityModel(
                            "Ashgabat tm "+i,
                            "Ashgabat ru "+i,
                            "Ashgabat en "+i,
                            "ashgabat "+i,
                            "1",
                            "https://teklipyaz.com/images/store/Citys/City1/3d2851.jpg",
                                i+""));
        }*/

        CenterRepository.getCenterRepository().setListOfCity(ListOfCity);
    }

    public void getAllOrganizations(int city) {
        Log.i("Information","City: "+city);

    }

}

