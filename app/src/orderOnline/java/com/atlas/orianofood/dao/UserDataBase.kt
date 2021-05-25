package com.atlas.orianofood.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atlas.orianofood.model_Register.UserInfo

@Database(entities = [UserInfo::class],version = 2)
abstract class UserDataBase :RoomDatabase(){
    abstract val userDao :UserDao

    companion object{
        @Volatile
        private var INSTANCE:UserDataBase?=null
        fun getInstance(context: Context):UserDataBase{
            synchronized(this){
                var instance:UserDataBase?= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,UserDataBase::class.java,
                        "user_data")
                            .fallbackToDestructiveMigration()
                            .build()


                }
                return instance

            }


        }
    }



}