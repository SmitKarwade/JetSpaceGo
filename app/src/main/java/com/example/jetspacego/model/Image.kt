package com.example.jetspacego.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("image_url") @Expose val imageUrl: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("thumbnail_url") @Expose val thumbnailUrl: String
) :Parcelable