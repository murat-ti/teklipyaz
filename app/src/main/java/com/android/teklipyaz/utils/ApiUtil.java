package com.android.teklipyaz.utils;

import com.android.teklipyaz.domains.network.RetrofitAPI;
import com.android.teklipyaz.domains.network.RetrofitInterface;

public class ApiUtil {

    private static final String BASE_URL = "https://teklipyaz.com/";
    //private static final String BASE_URL = "https://dodago.ge/";
    //private static final String BASE_URL = "http://192.168.1.100/teklipyaz/";
    public static final String BASE_IMAGE_URL = BASE_URL+"images/store/";

    public static RetrofitInterface getServiceClass(){
        return RetrofitAPI.getRetrofit(BASE_URL).create(RetrofitInterface.class);
    }
}
