package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Orbit (

    @SerializedName("id"             ) var id            : Int?           = null,
    @SerializedName("name"           ) var name          : String?        = null,
    @SerializedName("abbrev"         ) var abbrev        : String?        = null,
    @SerializedName("celestial_body" ) var celestialBody : CelestialBodyO? = CelestialBodyO()

) : Parcelable