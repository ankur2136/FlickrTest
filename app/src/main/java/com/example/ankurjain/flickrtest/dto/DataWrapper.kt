package com.example.ankurjain.flickrtest.dto

import com.google.gson.annotations.SerializedName

data class DataWrapper (@SerializedName("page") val page: Int,
                        @SerializedName("photo") val listPhotos: List<GalleryItem>)