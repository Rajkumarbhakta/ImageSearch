package com.rkbapps.imagesearch.services;

import com.rkbapps.imagesearch.model.ImageModelClass;
import com.rkbapps.imagesearch.util.ApiData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Authorization: " + ApiData.API_KEY})
    @GET("curated")
    Call<ImageModelClass> getCuratedImages(@Query("page") Integer page, @Query("per_page") Integer perPage);

    @Headers({"Authorization: " + ApiData.API_KEY})
    @GET("search")
    Call<ImageModelClass> getSearchResult(@Query("query") String query, @Query("page") Integer page, @Query("per_page") Integer perPage);

}
