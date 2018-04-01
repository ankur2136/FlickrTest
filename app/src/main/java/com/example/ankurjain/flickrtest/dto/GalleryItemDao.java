package com.example.ankurjain.flickrtest.dto;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GalleryItemDao {

    @Query("SELECT * FROM gallery_item")
    List<GalleryItem> getAll();

    @Query("SELECT * FROM gallery_item where `query` LIKE  :input LIMIT 100")
    List<GalleryItem> findByQuery(String input);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertAll(GalleryItem... users);
}
