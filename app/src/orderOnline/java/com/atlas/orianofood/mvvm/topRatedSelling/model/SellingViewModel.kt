package com.atlas.orianofood.mvvm.topRatedSelling.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.core.App.Companion.appContext
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.retrofit.ApiService
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import com.atlas.orianofood.mvvm.topRatedSelling.SellingLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellingViewModel(
    val apiService: ApiService = RetrofitInstance.apiService,
    val appDatabase: AppDatabase = AppDatabase.getInstance(appContext)!!
) : ViewModel() {
    private val TAG: String = SellingViewModel::class.java.simpleName
    val sellingData: MutableLiveData<SellingData> by lazy { MutableLiveData<SellingData>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val tshowToast: SellingLiveEvent<String> by lazy { SellingLiveEvent<String>() }

    init {
        getSellingData()
    }

    private fun getSellingData() {
        apiService.getSellingData().enqueue(object : Callback<SellingData?> {
            override fun onResponse(
                    call: Call<SellingData?>?,
                    response: Response<SellingData?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("PMYTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        sellingData.postValue(response.body())

                        // tshowToast.postValue("data received (Toast shows one times)")

                        if (response.body()!!.slist.size > 0) {
                            saveInDB(response.body()!!.slist)
                        }
                    } else {
                        error.postValue(response.errorBody()!!.string())

                    }
                }
            }

            override fun onFailure(call: Call<SellingData?>?, t: Throwable?) {
                Log.e("ptag", t?.message.toString())
                error.postValue("error happened")
            }
        })
    }

    private fun saveInDB(slist: MutableList<SellingItem>) {
        for (i in slist) {
            Log.d(TAG, "${i.productName} inserted to database")
            appDatabase.topSellingDao.insertSellingData(i)
        }
    }
}