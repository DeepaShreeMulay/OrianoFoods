package com.atlas.orianofood.gallery.model

import com.google.gson.annotations.SerializedName

data class GalleryData (
        @SerializedName("status")
        var status: Int,
        @SerializedName("list")
        var glist: MutableList<GalleryItem>
        )