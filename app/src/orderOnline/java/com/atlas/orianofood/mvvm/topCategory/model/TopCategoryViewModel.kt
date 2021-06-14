package com.atlas.orianofood.mvvm.topCategory.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.core.App
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.retrofit.ApiService
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import com.atlas.orianofood.mvvm.topCategory.TopCategoryLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopCategoryViewModel (
        val apiService: ApiService = RetrofitInstance.apiService,
        val appDatabase: AppDatabase = AppDatabase.getInstance(App.appContext)!!
): ViewModel()  {
    private val TAG: String = TopCategoryViewModel::class.java.simpleName
    val topCategoryData: MutableLiveData<TopCategoryData> by lazy { MutableLiveData<TopCategoryData>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val tcshowToast: TopCategoryLiveEvent<String> by lazy { TopCategoryLiveEvent<String>() }

    init {
        getTopCategoryData()
    }

    private fun getTopCategoryData() {
        apiService.getTopCategoryData().enqueue(object : Callback<TopCategoryData?> {
            override fun onResponse(
                    call: Call<TopCategoryData?>?,
                    response: Response<TopCategoryData?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("planMYTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        topCategoryData.postValue(response.body())

                        tcshowToast.postValue("data received")

                        if (response.body()!!.tclist.size > 0) {
                            saveInDB(response.body()!!.tclist)
                        }
                    } else {
                        error.postValue(response.errorBody()!!.string())

                    }
                }
            }
            override fun onFailure(call: Call<TopCategoryData?>?, t: Throwable?) {
                Log.e("ptag", t?.message.toString())
                error.postValue("error happened")
            }
        })
    }

    private fun saveInDB(tclist: MutableList<TopCategoryItem>) {
        for (i in tclist) {
            Log.d(TAG, "${i.categoryName} inserted to database")
            appDatabase.topCategoryDao.insertTopCategoryData(i)
        }
    }
}