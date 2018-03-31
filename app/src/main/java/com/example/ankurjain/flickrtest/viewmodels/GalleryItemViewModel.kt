package com.example.ankurjain.flickrtest.viewmodels

import android.databinding.BaseObservable

open class GalleryItemViewModel : BaseObservable() {

    private var mUrl: String? = ""

    fun setUrl(text: String) {
        mUrl = text
        notifyChange()
    }

    fun getUrl(): String? {
        return mUrl
    }

}