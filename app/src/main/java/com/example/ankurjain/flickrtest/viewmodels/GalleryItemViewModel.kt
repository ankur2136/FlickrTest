package com.example.ankurjain.flickrtest.viewmodels

import android.databinding.BaseObservable

open class GalleryItemViewModel : BaseObservable() {

    var mText: String? = ""

    fun setText(text: String) {
        mText = text
        notifyChange()
    }

    fun getText(): String? {
        return mText
    }

}