package com.atlas.orianofood.category


import com.atlas.orianofood.gallery.model.GalleryData
import com.atlas.orianofood.category.model.CategoryData
import com.atlas.orianofood.topRatedProduct.model.TopRatedData
import com.atlas.orianofood.topRatedSelling.model.SellingData


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {


      //@Headers("Authorization : Bearer WLNHm5gZ1IiyMF2AWivBmuEIrf9Cu1GAUcOXvzrL")
        @GET("v1/category")
        fun getCategoryData(): Call<CategoryData>

    @GET("v1/toprated-product")
    fun getTopData(): Call<TopRatedData>

    @GET("v1/topselling-product")
    fun getSellingData(): Call<SellingData>

    @GET("v1/gallery")
    fun getGalleryData(): Call<GalleryData>

}