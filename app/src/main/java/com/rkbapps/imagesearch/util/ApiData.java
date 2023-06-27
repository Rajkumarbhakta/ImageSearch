package com.rkbapps.imagesearch.util;

public class ApiData {

    public static final String API_KEY = "x9Yf1QT8KXSpwH4ILI0smrHAIGY7BwFarViZrYM8jxlY2vABSWIOyDX5";

    public static String getUrl(String query, Integer page) {
        return "https://api.pexels.com/v1/search/?page=" + page + "&per_page=30&query=" + query;
    }

    public static final String BASE_URL = "https://api.pexels.com/v1/";



}
