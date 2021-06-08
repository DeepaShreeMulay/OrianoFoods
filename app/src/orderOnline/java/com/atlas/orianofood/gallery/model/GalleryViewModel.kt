package com.atlas.orianofood.gallery.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.gallery.GalleryLiveEvent
import com.atlas.orianofood.gallery.dao.GalleryDatabase
import com.atlas.orianofood.category.ApiService
import com.atlas.orianofood.registerApi.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryViewModel (
        val apiService: ApiService = RetrofitInstance.apiService!!,
        val appDatabase: GalleryDatabase = GalleryDatabase.getInstance()
): ViewModel() {
    private val TAG: String = GalleryViewModel::class.java.simpleName
    val galleryData: MutableLiveData<GalleryData> by lazy { MutableLiveData<GalleryData>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val gshowToast: GalleryLiveEvent<String> by lazy { GalleryLiveEvent<String>() }

    init {
        getGalleryData()
    }

    private fun getGalleryData() {
        apiService.getGalleryData().enqueue(object : Callback<GalleryData?> {
            override fun onResponse(
                    call: Call<GalleryData?>?,
                    response: Response<GalleryData?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("PMYTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        galleryData.postValue(response.body())

                        gshowToast.postValue("data received (Toast shows one times)")

                        if (response.body()!!.glist.size > 0) {
                            saveInDB(response.body()!!.glist)
                        }
                    } else {
                        error.postValue(response.errorBody()!!.string())

                    }
                }
            }
            override fun onFailure(call: Call<GalleryData?>?, t: Throwable?) {
                Log.e("ptag", t?.message.toString())
                error.postValue("error happened")
            }
        })
    }

    private fun saveInDB(glist: MutableList<GalleryItem>) {
        for (i in glist) {
            Log.d(TAG, "${i.postName} inserted to database")
            appDatabase.galleryDao().insertGalleryData(i)
        }
    }
}