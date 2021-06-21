package com.atlas.orianofood.mvvm.product.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atlas.orianofood.core.App
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.product.ProductLiveEvent
import com.atlas.orianofood.mvvm.retrofit.ApiService
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(
        val apiService: ApiService = RetrofitInstance.apiService,
        val appDatabase: AppDatabase = AppDatabase.getInstance(App.appContext)!!
) : ViewModel() {
    private val TAG: String = ProductViewModel::class.java.simpleName
    val productData: MutableLiveData<ProductData> by lazy { MutableLiveData<ProductData>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val pshowToast: ProductLiveEvent<String> by lazy { ProductLiveEvent<String>() }

    init {
        getProductData()
    }

    private fun getProductData() {
        apiService.getProductData().enqueue(object : Callback<ProductData?> {
            override fun onResponse(
                    call: Call<ProductData?>?,
                    response: Response<ProductData?>?
            ) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")

                Log.d("PMYTAG", response?.body().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        productData.postValue(response.body())

                        // pshowToast.postValue("data received ")

                        if (response.body()!!.plist.size > 0) {
                            saveInDB(response.body()!!.plist)
                        }
                    } else {
                        error.postValue(response.errorBody()!!.string())

                    }
                }
            }

            override fun onFailure(call: Call<ProductData?>?, t: Throwable?) {
                Log.e("ptag", t?.message.toString())
                error.postValue("error happened")
            }
        })
    }

    private fun saveInDB(plist: MutableList<ProductItems>) {
        for (i in plist) {
            Log.d(TAG, "${i.productName} inserted to database")
            appDatabase.productDao.insertProductData(i)
        }

    }
}

