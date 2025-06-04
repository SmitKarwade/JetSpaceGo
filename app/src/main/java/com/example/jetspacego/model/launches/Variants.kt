package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Variants (

    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("type"      ) var type     : Type?   = Type(),
    @SerializedName("image_url" ) var imageUrl : String? = null

) : Parcelable