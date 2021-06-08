package com.atlas.orianofood.topRatedProduct.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.core.App
import com.atlas.orianofood.topRatedProduct.model.TopRatedItem

@Database(entities = [TopRatedItem::class], version = TopRatedDatabase.VERSION)
abstract class TopRatedDatabase : RoomDatabase() {
    abstract fun topRatedDao():TopRatedDao

    companion object {
        const val DB_NAME = "topRated.db"
        const val VERSION = 1
        private val instance: TopRatedDatabase by lazy {create(App.instance) }

        @Synchronized
        internal fun getInstance(): TopRatedDatabase {
            return instance
        }
        private fun create(context: Context): TopRatedDatabase {
            return Room.databaseBuilder(context, TopRatedDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}