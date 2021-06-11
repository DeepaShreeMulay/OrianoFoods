package com.atlas.orianofood.mvvm.utils

import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App.Companion.appContext
import com.bumptech.glide.Glide


object CustomImageBindingAdapter {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView?, url: String?) {

        try {
            if (imageView != null) {

                Glide.with(appContext)
                    .load(url)
                    .placeholder(R.drawable.ic_curry_food)
                    .error(R.drawable.ic_cooking_food_fried_rice)
                    .into(imageView)

            }

        } catch (ex: Exception) {

            imageView?.setImageResource(R.drawable.ic_cooking_food_fried_rice)

        }

    }
}