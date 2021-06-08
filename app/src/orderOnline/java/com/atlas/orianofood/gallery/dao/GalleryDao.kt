package com.atlas.orianofood.gallery.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.gallery.model.GalleryItem

@Dao
interface GalleryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGalleryData(galleryItem: GalleryItem): Long
    @Query("SELECT * FROM GalleryItem ")
    fun selectAllData(): MutableList<GalleryItem>
}