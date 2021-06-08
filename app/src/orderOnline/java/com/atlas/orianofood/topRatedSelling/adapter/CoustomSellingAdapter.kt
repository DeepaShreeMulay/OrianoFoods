package com.atlas.orianofood.topRatedSelling.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.atlas.orianofood.R
import com.squareup.picasso.Picasso

object CoustomSellingAdapter {
    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(simageView: ImageView?, url: String?) {

        try {
            Picasso.get().load(url).placeholder(R.mipmap.bg_home)
                .into(simageView)

        } catch (ex: Exception) {

            simageView?.setImageResource(R.mipmap.bg_home)

        }

    }
}