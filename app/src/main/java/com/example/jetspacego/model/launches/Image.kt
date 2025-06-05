package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image (

    @SerializedName("id"            ) var id           : Int?              = null,
    @SerializedName("name"          ) var name         : String?           = null,
    @SerializedName("image_url"     ) var imageUrl     : String?           = null,
    @SerializedName("thumbnail_url" ) var thumbnailUrl : String?           = null,
    @SerializedName("single_use"    ) var singleUse    : Boolean?          = null

) : Parcelable