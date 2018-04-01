package com.example.ankurjain.flickrtest.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gallery_item")
data class GalleryItem(@PrimaryKey(autoGenerate = false) @SerializedName("id") val id: String,
                       @ColumnInfo(name = "url_s") @SerializedName("url_s") val url: String,
                       @ColumnInfo(name = "owner") @SerializedName("owner") val owner: String?,
                       @ColumnInfo(name = "width_s") @SerializedName("width_s") val width: Int?,
                       @ColumnInfo(name = "height_s") @SerializedName("height_s") val height: Int?,
                       @ColumnInfo(name = "query") var query: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(url)
        parcel.writeString(owner)
        parcel.writeValue(width)
        parcel.writeValue(height)
        parcel.writeString(query)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GalleryItem> {
        override fun createFromParcel(parcel: Parcel): GalleryItem {
            return GalleryItem(parcel)
        }

        override fun newArray(size: Int): Array<GalleryItem?> {
            return arrayOfNulls(size)
        }
    }
}