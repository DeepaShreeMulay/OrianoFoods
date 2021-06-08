package com.atlas.orianofood.gallery.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class GalleryItem (
        @PrimaryKey(autoGenerate = true)
        @SerializedName("ID")
        var iD: Int,
        @SerializedName("meta_value")
        var metaValue: String,
        @SerializedName("post_name")
        var postName: String,
        @SerializedName("post_type")
        var postType: String,
        @SerializedName("url")
        var url: String
        )