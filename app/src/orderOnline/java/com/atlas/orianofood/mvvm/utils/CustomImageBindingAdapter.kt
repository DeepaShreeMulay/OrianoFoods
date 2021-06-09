package com.atlas.orianofood.mvvm.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.atlas.orianofood.R
import com.squareup.picasso.Picasso

object CustomImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView?, url: String?) {

        try {
            Picasso.get().load(url).placeholder(R.mipmap.bg_home)
                .into(imageView)

        } catch (ex: Exception) {

            imageView?.setImageResource(R.mipmap.bg_home)

        }

    }
}