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
    @SerializedName("credit"        ) var credit       : String?           = null,
    @SerializedName("license"       ) var license      : License?          = License(),
    @SerializedName("single_use"    ) var singleUse    : Boolean?          = null,
    @SerializedName("variants"      ) var variants     : ArrayList<Variants> = arrayListOf()

) : Parcelable