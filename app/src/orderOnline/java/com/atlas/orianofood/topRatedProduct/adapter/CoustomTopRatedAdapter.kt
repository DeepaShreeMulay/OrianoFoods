package com.atlas.orianofood.topRatedProduct.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.atlas.orianofood.R
import com.squareup.picasso.Picasso

object CoustomTopRatedAdapter {
    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(timageView: ImageView?, url: String?) {

        try {
            Picasso.get().load(url).placeholder(R.mipmap.bg_home)
                .into(timageView)

        } catch (ex: Exception) {

            timageView?.setImageResource(R.mipmap.bg_home)

        }

    }
}