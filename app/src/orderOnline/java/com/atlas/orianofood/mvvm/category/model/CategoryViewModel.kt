package com.atlas.orianofood.mvvm.category.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.core.App.Companion.appContext
import com.atlas.orianofood.mvvm.category.CategoryLiveEvent
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.retrofit.ApiService
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(
    val apiService: ApiService = RetrofitInstance.apiService,
    val appDatabase: AppDatabase = AppDatabase.getInstance(appContext)!!

):ViewModel () {
    private val TAG: String = CategoryViewModel::class.java.simpleName
    val homeData: MutableLiveData<CategoryData> by lazy { MutableLiveData<CategoryData>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val showToast : CategoryLiveEvent<String> by lazy { CategoryLiveEvent<String>() }

    init {
        getData()
    }

    private fun getData() {
        apiService.getCategoryData().enqueue(object : Callback<CategoryData?> {

            override fun onResponse(
                    call: Call<CategoryData?>?,
                    response: Response<CategoryData?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("MYTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        homeData.postValue(response.body())

                        //showToast.postValue("data received (Toast shows one times)")


                        if (response.body()!!.list.size > 0) {
                            saveInDB(response.body()!!.list)
                        }

                    } else {
                        error.postValue(response.errorBody()!!.string())

                    }
                }
            }

            override fun onFailure(call: Call<CategoryData?>?, t: Throwable?) {
                Log.e("tag", t?.message.toString())
                error.postValue("error happened")
            }


        })

    }

    private fun saveInDB(list: MutableList<CategoryItem>) {
        for (i in list) {
            Log.d(TAG, "${i.categoryName} inserted to database")
            appDatabase.categoryDao.insertData(i)
        }

    }
}