package com.atlas.orianofood.mvvm.retrofit

import com.atlas.orianofood.mvvm.category.model.CategoryData
import com.atlas.orianofood.mvvm.gallery.model.GalleryData
import com.atlas.orianofood.mvvm.getProfile.model.ProfileItems
import com.atlas.orianofood.mvvm.product.model.ProductData
import com.atlas.orianofood.mvvm.topCategory.model.TopCategoryData
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedData
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v1/category")
    fun getCategoryData(): Call<CategoryData>

    @GET("v1/toprated-product")
    fun getTopData(): Call<TopRatedData>

    @GET("v1/topselling-product")
    fun getSellingData(): Call<SellingData>

    @GET("v1/gallery")
    fun getGalleryData(): Call<GalleryData>

    @GET("v1/top-category")
    fun getTopCategoryData(): Call<TopCategoryData>

    @GET("v1/get-profile/{user_id}")
    fun getProfileData(@Path("user_id") userId: String): Call<ProfileItems>

    @GET("v1/products")
    fun getProductData(): Call<ProductData>

}