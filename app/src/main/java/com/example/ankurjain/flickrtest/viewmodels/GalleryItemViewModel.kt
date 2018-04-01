package com.example.ankurjain.flickrtest.viewmodels

import android.databinding.BaseObservable
import com.example.ankurjain.flickrtest.dto.GalleryItem

open class GalleryItemViewModel : BaseObservable() {

    private var mItem: GalleryItem? = null

    fun setItem(item: GalleryItem) {
        mItem = item
        notifyChange()
    }

    fun getItem(): GalleryItem? {
        return mItem
    }

}