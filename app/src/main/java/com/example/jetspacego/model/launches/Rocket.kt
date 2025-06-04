package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rocket (

    @SerializedName("id"            ) var id            : Int?           = null,
    @SerializedName("configuration" ) var configuration : Configuration? = Configuration()

) : Parcelable