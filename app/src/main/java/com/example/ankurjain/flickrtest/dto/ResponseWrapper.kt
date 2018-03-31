package com.example.ankurjain.flickrtest.dto

import com.google.gson.annotations.SerializedName

data class ResponseWrapper (@SerializedName("photos") val photos: DataWrapper,
                            @SerializedName("stat") val stat: String)