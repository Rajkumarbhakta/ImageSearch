package com.rkbapps.imagesearch.services;

import com.rkbapps.imagesearch.util.ApiData;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiData.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static ApiInterface api = null;

    public static ApiInterface getApi() {
        if (api == null) {
            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }

}
