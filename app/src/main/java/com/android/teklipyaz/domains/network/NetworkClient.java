package com.android.teklipyaz.domains.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    public static Retrofit retrofit;

    public void NetworkClient(){

    }

    public static Retrofit getRetrofit(String baseUrl){

        if( retrofit == null ) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(logging).build();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson customGson = gsonBuilder.create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(customGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
