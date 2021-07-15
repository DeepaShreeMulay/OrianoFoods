package com.atlas.orianofood.mvvm.getProfile.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App
import com.atlas.orianofood.core.App.Companion.appContext
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.getProfile.ProfileLiveEvent
import com.atlas.orianofood.mvvm.retrofit.ApiService
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(
    val apiService: ApiService = RetrofitInstance.apiService,
    val appDatabase: AppDatabase = AppDatabase.getInstance(App.appContext)!!
) : ViewModel() {
    private val TAG: String = ProfileViewModel::class.java.simpleName
    val profileData: MutableLiveData<ProfileItems> by lazy { MutableLiveData<ProfileItems>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val profileshowToast: ProfileLiveEvent<String> by lazy { ProfileLiveEvent<String>() }
    val DUMMY_EMAIL: String = "useremail@gmail.com"

    init {
        getprofileData()
    }

    private fun getprofileData() {
        apiService.getProfileData(getUserId()).enqueue(object : Callback<ProfileItems?> {
            override fun onResponse(
                call: Call<ProfileItems?>?,
                response: Response<ProfileItems?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("ProfileTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        profileData.postValue(response.body())
                        Log.e("ProfileViewModel", response.body()!!.id.toString())
                        //  profileshowToast.postValue("ProfileData received")

                        val resp: String =
                            if (response.body()!!.userAltEmail != null && response.body()!!.userAltEmail != DUMMY_EMAIL) {
                                response.body()!!.userAltEmail
                            } else {
                                DUMMY_EMAIL
                            }
                        saveInDB(
                            ProfileItems(
                                response.body()!!.id,
                                response.body()!!.displayName,
                                response.body()!!.userEmail,
                                response.body()!!.userLogin,
                                response.body()!!.userNicename,
                                resp
                            )
                        )


                        /*if (response.body()!!.id > 0) {
                           // Log.e("ProfileViewModel", response.body()!!.displayName)
                        }*/
                    } else {
                        error.postValue(response.errorBody()!!.string())

                    }
                }
            }

            override fun onFailure(call: Call<ProfileItems?>?, t: Throwable?) {
                Log.e("profiletag", t?.message.toString())
                error.postValue("error happened")
            }
        })
    }

    /* fun addUserId(context: Context,userId:String){
         val sharedPreferences:SharedPreferences=context.getSharedPreferences(appContext.getString(R.string.preference_file_key),Context.MODE_PRIVATE)
         val editor:SharedPreferences.Editor=sharedPreferences.edit()
         editor.putString("userId",userId)
         editor.apply()
         editor.commit()
     }*/
    private fun getUserId(): String {
        val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
            appContext.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("userId", "") ?: ""
    }

    private fun saveInDB(profileItems: ProfileItems) {
        appDatabase.profileDao.insertProfileData(profileItems)
    }
}