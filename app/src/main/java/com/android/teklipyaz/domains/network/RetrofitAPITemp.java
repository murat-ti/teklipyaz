package com.android.teklipyaz.domains.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPITemp {
    private static Retrofit retrofit = null;
    public static Retrofit getRetrofit(String url) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    //.client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(url)
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient buildClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

}