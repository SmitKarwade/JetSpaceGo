package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MissionPatches (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("priority"      ) var priority     : Int?    = null,
    @SerializedName("image_url"     ) var imageUrl     : String? = null,
    @SerializedName("agency"        ) var agency       : LaunchServiceProvider? = LaunchServiceProvider(),
    @SerializedName("response_mode" ) var responseMode : String? = null

) : Parcelable