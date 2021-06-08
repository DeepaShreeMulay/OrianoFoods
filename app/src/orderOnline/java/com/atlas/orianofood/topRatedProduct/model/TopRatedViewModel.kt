package com.atlas.orianofood.topRatedProduct.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.category.ApiService
import com.atlas.orianofood.registerApi.RetrofitInstance
import com.atlas.orianofood.topRatedProduct.TopLiveEvent
import com.atlas.orianofood.topRatedProduct.dao.TopRatedDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedViewModel (
        val apiService: ApiService = RetrofitInstance.apiService,
        val appDatabase: TopRatedDatabase = TopRatedDatabase.getInstance()
): ViewModel() {
    private val TAG: String = TopRatedViewModel::class.java.simpleName
    val topRatedData: MutableLiveData<TopRatedData> by lazy { MutableLiveData<TopRatedData>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val topshowToast: TopLiveEvent<String> by lazy { TopLiveEvent<String>() }

    init {
        getTopData()
    }

    private fun getTopData() {
        apiService.getTopData().enqueue(object : Callback<TopRatedData?> {
            override fun onResponse(
                    call: Call<TopRatedData?>?,
                    response: Response<TopRatedData?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("PMYTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        topRatedData.postValue(response.body())

                        topshowToast.postValue("data received (Toast shows one times)")

                        if (response.body()!!.rlist.size > 0) {
                            saveInDB(response.body()!!.rlist)
                        }
                    } else {
                        error.postValue(response.errorBody()!!.string())

                    }
                }
            }
            override fun onFailure(call: Call<TopRatedData?>?, t: Throwable?) {
                Log.e("ptag", t?.message.toString())
                error.postValue("error happened")
            }
        })
    }

    private fun saveInDB(rlist: MutableList<TopRatedItem>) {
        for (i in rlist) {
            Log.d(TAG, "${i.productName} inserted to database")
            appDatabase.topRatedDao().insertTopRatedData(i)
        }
    }
}