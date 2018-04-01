package com.example.ankurjain.flickrtest.viewmodels

import android.databinding.BaseObservable
import android.view.View
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

    fun getTitleVisibility(): Int {
        if (mItem?.title.isNullOrEmpty()){
            return View.GONE
        }
        return View.VISIBLE
    }

    fun getOwnerVisibility(): Int {
        if (mItem?.owner.isNullOrEmpty()){
            return View.GONE
        }
        return View.VISIBLE
    }

}