package com.rkbapps.imagesearch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Photo(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("width")
    @Expose
    var width: Int,
    @SerializedName("height")
    @Expose
    var height: Int,
    @SerializedName("url")
    @Expose
    var url: String,
    @SerializedName("photographer")
    @Expose
    var photographer: String,
    @SerializedName("photographer_url")
    @Expose
    var photographerUrl: String,
    @SerializedName("photographer_id")
    @Expose
    var photographerId: Int,
    @SerializedName("avg_color")
    @Expose
    var avgColor: String,
    @SerializedName("src")
    @Expose
    var src: Src,
    @SerializedName("alt")
    @Expose
    var alt: String
) : Serializable