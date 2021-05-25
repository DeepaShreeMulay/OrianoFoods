package com.atlas.orianofood.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.model_Register.LoginInfo

@Database(entities = [LoginInfo::class],version = 4)
abstract class LoginDataBase : RoomDatabase(){
    abstract val loginDao:LoginDao
    companion object{
        @Volatile
        private var INSTANCE:LoginDataBase?=null
        fun getInstance(context: Context):LoginDataBase{
            synchronized(this){
                var instance:LoginDataBase?= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                            context.applicationContext,LoginDataBase::class.java,
                            "login_data")
                            .fallbackToDestructiveMigration()
                            .build()


                }
                return instance

            }


        }
    }
}