package com.atlas.orianofood.category.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.atlas.orianofood.R
import com.squareup.picasso.Picasso

object CoustomBindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView, url: String) {

        Picasso.get().load(url).placeholder(R.mipmap.bg_home)
                .into(imageView)


    }

}