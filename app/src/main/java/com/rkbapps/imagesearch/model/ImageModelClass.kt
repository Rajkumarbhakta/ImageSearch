package com.rkbapps.imagesearch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageModelClass(
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("per_page")
    @Expose
    val perPage: Int,
    @SerializedName("photos")
    @Expose
    val photos: List<Photo>,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int,
    @SerializedName("next_page")
    @Expose
    val nextPage: String,
    @SerializedName("prev_page")
    @Expose
    val prevPage: String
)