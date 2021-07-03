package com.atlas.orianofood.mvvm.getProfile.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.core.App
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.getProfile.ProfileLiveEvent
import com.atlas.orianofood.mvvm.retrofit.ApiService
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel  (
        val apiService: ApiService = RetrofitInstance.apiService,
        val appDatabase: AppDatabase = AppDatabase.getInstance(App.appContext)!!
): ViewModel()   {
    private val TAG: String = ProfileViewModel::class.java.simpleName
    val profileData: MutableLiveData<ProfileItems> by lazy { MutableLiveData<ProfileItems>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val profileshowToast: ProfileLiveEvent<String> by lazy { ProfileLiveEvent<String>() }



    init {
        getprofileData()
    }

    private fun getprofileData() {
        apiService.getProfileData().enqueue(object : Callback<ProfileItems?> {
            override fun onResponse(
                    call: Call<ProfileItems?>?,
                    response: Response<ProfileItems?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("ProfileTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        profileData.postValue(response.body())

                        //  profileshowToast.postValue("ProfileData received")

                        if (response.body()!!.id > 0) {
                            saveInDB(ProfileItems(1, "", "", "", ""))
                        }
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

    private fun saveInDB(profileItems: ProfileItems){

        appDatabase.profileDao.insertProfileData(profileItems)
    }
}