package com.atlas.orianofood.topRatedSelling.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.category.ApiService
import com.atlas.orianofood.registerApi.RetrofitInstance
import com.atlas.orianofood.topRatedSelling.SellingLiveEvent
import com.atlas.orianofood.topRatedSelling.dao.SellingDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellingViewModel  (
        val apiService: ApiService = RetrofitInstance.apiService,
        val appDatabase: SellingDatabase = SellingDatabase.getInstance()
): ViewModel() {
    private val TAG: String = SellingViewModel::class.java.simpleName
    val sellingData: MutableLiveData<SellingData> by lazy { MutableLiveData<SellingData>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val tshowToast : SellingLiveEvent<String> by lazy { SellingLiveEvent<String>() }

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

                        tshowToast.postValue("data received (Toast shows one times)")

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
            appDatabase.sellingDao().insertSellingData(i)
        }
    }
}