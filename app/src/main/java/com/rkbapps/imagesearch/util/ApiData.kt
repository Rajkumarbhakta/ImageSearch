package com.rkbapps.imagesearch.util

import com.rkbapps.imagesearch.BuildConfig


object ApiData {
    const val API_KEY = BuildConfig.API_KEY
    fun getUrl(query: String, page: Int): String {
        return "https://api.pexels.com/v1/search/?page=$page&per_page=30&query=$query"
    }
    const val BASE_URL = "https://api.pexels.com/v1/"
}