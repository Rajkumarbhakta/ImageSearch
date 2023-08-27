package com.rkbapps.imagesearch.services

import com.rkbapps.imagesearch.model.ImageModelClass
import com.rkbapps.imagesearch.util.ApiData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers("Authorization: " + ApiData.API_KEY)
    @GET("curated")
     fun getCuratedImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Response<ImageModelClass>

    @Headers("Authorization: " + ApiData.API_KEY)
    @GET("search")
    fun getSearchResult(
        @Query("query") query: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<ImageModelClass?>?
}