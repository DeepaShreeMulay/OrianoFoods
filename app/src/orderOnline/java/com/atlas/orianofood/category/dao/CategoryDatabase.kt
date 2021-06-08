package com.atlas.orianofood.category.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.category.model.CtegoryItem
import com.atlas.orianofood.core.App

@Database(entities = [CtegoryItem::class], version = CategoryDatabase.VERSION)
abstract class CategoryDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DB_NAME = "category.db"
        const val VERSION = 1
        private val instance: CategoryDatabase by lazy { create(App.instance) }

        @Synchronized
        internal fun getInstance(): CategoryDatabase {
            return instance
        }

        private fun create(context: Context): CategoryDatabase {
            return Room.databaseBuilder(context, CategoryDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
        }

    }
}