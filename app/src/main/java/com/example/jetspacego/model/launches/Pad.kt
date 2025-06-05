package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pad (

    @SerializedName("id"                           ) var id                        : Int?              = null,
    @SerializedName("active"                       ) var active                    : Boolean?          = null,
    @SerializedName("agencies"                     ) var agencies                  : ArrayList<Agencies> = arrayListOf(),
    @SerializedName("name"                         ) var name                      : String?           = null,
    @SerializedName("image"                        ) var image                     : Image?           = Image(),
    @SerializedName("description"                  ) var description               : String?           = null,
    @SerializedName("info_url"                     ) var infoUrl                   : String?           = null,
    @SerializedName("wiki_url"                     ) var wikiUrl                   : String?           = null,
    @SerializedName("map_url"                      ) var mapUrl                    : String?           = null,
    @SerializedName("latitude"                     ) var latitude                  : Double?           = null,
    @SerializedName("longitude"                    ) var longitude                 : Double?           = null,
    @SerializedName("country"                      ) var country                   : Country?          = Country(),
    @SerializedName("map_image"                    ) var mapImage                  : String?           = null,
    @SerializedName("total_launch_count"           ) var totalLaunchCount          : Int?              = null,
    @SerializedName("orbital_launch_attempt_count" ) var orbitalLaunchAttemptCount : Int?              = null,
    @SerializedName("fastest_turnaround"           ) var fastestTurnaround         : String?           = null,
    @SerializedName("location"                     ) var location                  : Location?         = Location()

) : Parcelable