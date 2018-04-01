package com.example.ankurjain.flickrtest

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.example.ankurjain.flickrtest.activities.SearchActivity
import com.example.ankurjain.flickrtest.database.AppDatabase
import com.example.ankurjain.flickrtest.dto.GalleryItem

class DbHelper {

    companion object {
        val INSTANCE = DbHelper()
    }

    fun insertGalleryItemToDb(context: Context, item: GalleryItem, query: String) {
        item.query = query
        val runnable = Runnable {
            val db = AppDatabase.getAppDatabase(context)
            addItemToDb(db, item)
        }

        val th = Thread(runnable)
        th.start()
    }

    private fun addItemToDb(db: AppDatabase, item: GalleryItem): GalleryItem {
        try {
            db.galleryDao().insertAll(item)
        } catch (exception: SQLiteConstraintException) {
            //Do nothing as the item already exists in the db.
        }
        return item
    }

    fun getItemsForQuery(context: Context, query: String, interaction: SearchActivity.IDbHelper) {
        val runnable = Runnable {
            try {
                val db = AppDatabase.getAppDatabase(context)
                val items = db.galleryDao().findByQuery(query)
                interaction.onOperationComplete(items)
            } catch (e: Exception) {
                interaction.onOperationFailed(e)
            }
        }

        val th = Thread(runnable)
        th.start()
    }

}