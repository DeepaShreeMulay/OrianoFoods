package com.atlas.orianofood.mvvm.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class GalleryViewHolder  <T>
(val gallerydatabinding: ViewDataBinding)
    : RecyclerView.ViewHolder(gallerydatabinding.root){

    constructor(@NonNull inflater: LayoutInflater, @IdRes layoutId: Int,
                @NonNull parent: ViewGroup, @NonNull attachToParent: Boolean) :
            this(DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, attachToParent))


    abstract fun onBind(t: T)
}