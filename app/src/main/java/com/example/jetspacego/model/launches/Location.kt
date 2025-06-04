package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (

    @SerializedName("response_mode"       ) var responseMode      : String?        = null,
    @SerializedName("id"                  ) var id                : Int?           = null,
    @SerializedName("url"                 ) var url               : String?        = null,
    @SerializedName("name"                ) var name              : String?        = null,
    @SerializedName("celestial_body"      ) var celestialBody     : CelestialBodyL? = CelestialBodyL(),
    @SerializedName("active"              ) var active            : Boolean?       = null,
    @SerializedName("country"             ) var country           : Country?       = Country(),
    @SerializedName("description"         ) var description       : String?        = null,
    @SerializedName("image"               ) var image             : Image?         = Image(),
    @SerializedName("map_image"           ) var mapImage          : String?        = null,
    @SerializedName("longitude"           ) var longitude         : Double?        = null,
    @SerializedName("latitude"            ) var latitude          : Double?        = null,
    @SerializedName("timezone_name"       ) var timezoneName      : String?        = null,
    @SerializedName("total_launch_count"  ) var totalLaunchCount  : Int?           = null,
    @SerializedName("total_landing_count" ) var totalLandingCount : Int?           = null

) : Parcelable