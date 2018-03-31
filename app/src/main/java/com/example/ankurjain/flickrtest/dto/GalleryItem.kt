package com.example.ankurjain.flickrtest.dto

import com.google.gson.annotations.SerializedName

data class GalleryItem(@SerializedName("id") val id: String,
                       @SerializedName("url_s") val url: String,
                       @SerializedName("owner") val owner: String?,
                       @SerializedName("width_s") val width: Int?,
                       @SerializedName("height_s") val height: Int?)