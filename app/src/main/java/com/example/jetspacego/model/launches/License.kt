package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class License (

    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("priority" ) var priority : Int?    = null,
    @SerializedName("link"     ) var link     : String? = null

) : Parcelable