package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CelestialBodyL (
    @SerializedName("response_mode"            ) var responseMode           : String?  = null,
    @SerializedName("id"                       ) var id                     : Int?     = null,
    @SerializedName("name"                     ) var name                   : String?  = null,
    @SerializedName("type"                     ) var type                   : Type?    = Type(),
    @SerializedName("diameter"                 ) var diameter               : Int?     = null,
    @SerializedName("mass"                     ) var mass                   : Int?     = null,
    @SerializedName("gravity"                  ) var gravity                : Double?  = null,
    @SerializedName("length_of_day"            ) var lengthOfDay            : String?  = null,
    @SerializedName("atmosphere"               ) var atmosphere             : Boolean? = null,
    @SerializedName("image"                    ) var image                  : Image?   = Image(),
    @SerializedName("description"              ) var description            : String?  = null,
    @SerializedName("wiki_url"                 ) var wikiUrl                : String?  = null,
    @SerializedName("total_attempted_launches" ) var totalAttemptedLaunches : Int?     = null,
    @SerializedName("successful_launches"      ) var successfulLaunches     : Int?     = null,
    @SerializedName("failed_launches"          ) var failedLaunches         : Int?     = null,
    @SerializedName("total_attempted_landings" ) var totalAttemptedLandings : Int?     = null,
    @SerializedName("successful_landings"      ) var successfulLandings     : Int?     = null,
    @SerializedName("failed_landings"          ) var failedLandings         : Int?     = null

) : Parcelable