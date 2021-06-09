package com.atlas.orianofood.mvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.mvvm.category.dao.CategoryDao
import com.atlas.orianofood.mvvm.category.model.CategoryItem
import com.atlas.orianofood.mvvm.gallery.dao.GalleryDao
import com.atlas.orianofood.mvvm.gallery.model.GalleryItem
import com.atlas.orianofood.mvvm.login.dao.LoginDao
import com.atlas.orianofood.mvvm.login.model.LoginData
import com.atlas.orianofood.mvvm.register.dao.UserDao
import com.atlas.orianofood.mvvm.register.model.UserData
import com.atlas.orianofood.mvvm.topRatedProduct.dao.TopRatedDao
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedItem
import com.atlas.orianofood.mvvm.topRatedSelling.dao.SellingDao
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingItem


@Database(
    entities = [LoginData::class, UserData::class, CategoryItem::class, TopRatedItem::class, SellingItem::class, GalleryItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val loginDao: LoginDao
    abstract val userDao: UserDao
    abstract val categoryDao: CategoryDao
    abstract val topRatedDao: TopRatedDao
    abstract val topSellingDao: SellingDao
    abstract val galleryDao: GalleryDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            var instance: AppDatabase? = INSTANCE
            synchronized(AppDatabase::class.java) {
                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "orianodb"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }
}