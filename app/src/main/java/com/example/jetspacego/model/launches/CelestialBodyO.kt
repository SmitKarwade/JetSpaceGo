package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CelestialBodyO (

    @SerializedName("response_mode"            ) var responseMode           : String?  = null,
    @SerializedName("id"                       ) var id                     : Int?     = null,
    @SerializedName("name"                     ) var name                   : String?  = null,
) : Parcelable