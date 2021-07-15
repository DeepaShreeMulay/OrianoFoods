package com.atlas.orianofood.mvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.mvvm.category.dao.CategoryDao
import com.atlas.orianofood.mvvm.category.model.CategoryItem
import com.atlas.orianofood.mvvm.gallery.dao.GalleryDao
import com.atlas.orianofood.mvvm.gallery.model.GalleryItem
import com.atlas.orianofood.mvvm.getProfile.model.ProfileItems
import com.atlas.orianofood.mvvm.getProfile.model.dao.ProfileDao
import com.atlas.orianofood.mvvm.login.dao.LoginDao
import com.atlas.orianofood.mvvm.login.model.LoginData
import com.atlas.orianofood.mvvm.order.OrderDao
import com.atlas.orianofood.mvvm.order.OrderItem
import com.atlas.orianofood.mvvm.product.dao.ProductDao
import com.atlas.orianofood.mvvm.product.model.ProductItems
import com.atlas.orianofood.mvvm.register.dao.UserDao
import com.atlas.orianofood.mvvm.register.model.UserData
import com.atlas.orianofood.mvvm.setProfile.SetProfileDao
import com.atlas.orianofood.mvvm.setProfile.SetProfileItem
import com.atlas.orianofood.mvvm.topCategory.dao.TopCategoryDao
import com.atlas.orianofood.mvvm.topCategory.model.TopCategoryItem
import com.atlas.orianofood.mvvm.topRatedProduct.dao.TopRatedDao
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedItem
import com.atlas.orianofood.mvvm.topRatedSelling.dao.SellingDao
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingItem

// We need to have single DB for all tables
@Database(
    entities = [LoginData::class, UserData::class, CategoryItem::class, TopRatedItem::class, SellingItem::class, GalleryItem::class, TopCategoryItem::class, ProfileItems::class, ProductItems::class, OrderItem::class, SetProfileItem::class],
    version = 15,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val loginDao: LoginDao
    abstract val userDao: UserDao
    abstract val categoryDao: CategoryDao
    abstract val topRatedDao: TopRatedDao
    abstract val topSellingDao: SellingDao
    abstract val galleryDao: GalleryDao
    abstract val topCategoryDao:TopCategoryDao
    abstract val profileDao: ProfileDao
    abstract val productDao: ProductDao
    abstract val orderDao: OrderDao
    abstract val setProfileDao: SetProfileDao


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