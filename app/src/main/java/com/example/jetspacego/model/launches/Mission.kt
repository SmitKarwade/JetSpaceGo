package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mission (

    @SerializedName("id"          ) var id          : Int?              = null,
    @SerializedName("name"        ) var name        : String?           = null,
    @SerializedName("type"        ) var type        : String?           = null,
    @SerializedName("description" ) var description : String?           = null,
    @SerializedName("image"       ) var image       : String?           = null,
    @SerializedName("orbit"       ) var orbit       : Orbit?            = Orbit(),
    @SerializedName("agencies"    ) var agencies    : ArrayList<Agencies> = arrayListOf()

) : Parcelable