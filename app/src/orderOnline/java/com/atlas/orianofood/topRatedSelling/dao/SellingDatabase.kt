package com.atlas.orianofood.topRatedSelling.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.core.App
import com.atlas.orianofood.topRatedSelling.model.SellingItem

@Database(entities = [SellingItem::class], version = SellingDatabase.VERSION)
abstract class SellingDatabase : RoomDatabase() {
    abstract fun sellingDao(): SellingDao

    companion object {
        const val DB_NAME = "selling.db"
        const val VERSION = 1
        private val instance: SellingDatabase by lazy { create(App.instance) }

        @Synchronized
        internal fun getInstance(): SellingDatabase {
            return instance
        }

        private fun create(context: Context): SellingDatabase {
            return Room.databaseBuilder(context, SellingDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}