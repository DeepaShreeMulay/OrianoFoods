package com.atlas.orianofood.gallery.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.atlas.orianofood.R
import com.squareup.picasso.Picasso

object CoustomGalleryAdapter {
    @JvmStatic
    @BindingAdapter("bind:url")
    fun loadImage(gimageView: ImageView?, url: String?) {

        try {
            Picasso.get().load(url).placeholder(R.mipmap.bg_home)
                    .into(gimageView)

        } catch (ex: Exception) {

            gimageView?.setImageResource(R.mipmap.bg_home)

        }

    }
}