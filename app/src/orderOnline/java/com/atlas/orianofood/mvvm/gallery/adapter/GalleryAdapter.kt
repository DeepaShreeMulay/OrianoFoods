package com.atlas.orianofood.mvvm.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.databinding.GalleryItemBinding
import com.atlas.orianofood.mvvm.gallery.model.GalleryItem

class GalleryAdapter (private var gitems: MutableList<GalleryItem> = arrayListOf<GalleryItem>())
    : RecyclerView.Adapter<GalleryAdapter.GalleryHolder>()  {

    override fun getItemCount(): Int {
        return gitems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        val binding  = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        holder.onBind(gitems[position])
    }


    inner class GalleryHolder(galleryDataBinding: ViewDataBinding)
        : GalleryViewHolder<GalleryItem>(galleryDataBinding)  {
        override fun onBind(galleryItem: GalleryItem): Unit = with(galleryItem) {
            gallerydatabinding.setVariable(item, galleryItem)


        }
    }
    fun add(gresult: MutableList<GalleryItem>) {
        gitems.addAll(gresult)
        notifyDataSetChanged()
    }

    fun clear() {
        gitems.clear()
        notifyDataSetChanged()
    }
}