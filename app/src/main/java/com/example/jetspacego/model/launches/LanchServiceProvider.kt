package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaunchServiceProvider (

    @SerializedName("response_mode" ) var responseMode : String? = null,
    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("url"           ) var url          : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("abbrev"        ) var abbrev       : String? = null,
    @SerializedName("type"          ) var type         : Type?   = Type()

) : Parcelable