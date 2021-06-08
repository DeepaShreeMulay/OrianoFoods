package com.atlas.orianofood.topRatedProduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class TopRatedBindingViewHolder <T>
    (val topRatedDatabinding: ViewDataBinding)
    : RecyclerView.ViewHolder(topRatedDatabinding.root) {
    constructor(@NonNull inflater: LayoutInflater, @IdRes layoutId: Int,
                @NonNull parent: ViewGroup, @NonNull attachToParent: Boolean) :
            this(DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, attachToParent))


    abstract fun onBind(t: T)
}