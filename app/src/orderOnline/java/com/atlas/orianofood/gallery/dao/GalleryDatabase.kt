package com.atlas.orianofood.gallery.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.gallery.model.GalleryItem
import com.atlas.orianofood.core.App

@Database(entities = [GalleryItem::class], version = GalleryDatabase.VERSION)
abstract class GalleryDatabase:RoomDatabase() {
    abstract fun galleryDao(): GalleryDao

    companion object {
        const val DB_NAME = "gallery.db"
        const val VERSION = 1
        private val instance: GalleryDatabase by lazy { create(App.instance) }

        @Synchronized
        internal fun getInstance(): GalleryDatabase {
            return instance
        }

        private fun create(context: Context): GalleryDatabase {
            return Room.databaseBuilder(context, GalleryDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
        }

    }
}