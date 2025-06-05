package com.example.jetspacego.model.launches

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results (

    @SerializedName("id"                                 ) var id                             : String?                = null,
    @SerializedName("url"                                ) var url                            : String?                = null,
    @SerializedName("name"                               ) var name                           : String?                = null,
    @SerializedName("slug"                               ) var slug                           : String?                = null,
    @SerializedName("status"                             ) var status                         : Status?                = Status(),
    @SerializedName("last_updated"                       ) var lastUpdated                    : String?                = null,
    @SerializedName("net"                                ) var net                            : String?                = null,
    @SerializedName("image"                              ) var image                          : Image?                 = Image(),
    @SerializedName("probability"                        ) var probability                    : String?                = null,
    @SerializedName("weather_concerns"                   ) var weatherConcerns                : String?                = null,
    @SerializedName("failreason"                         ) var failreason                     : String?                = null,
    @SerializedName("launch_service_provider"            ) var launchServiceProvider          : LaunchServiceProvider? = LaunchServiceProvider(),
    @SerializedName("rocket"                             ) var rocket                         : Rocket?                = Rocket(),
    @SerializedName("mission"                            ) var mission                        : Mission?               = Mission(),
    @SerializedName("pad"                                ) var pad                            : Pad?                   = Pad(),
    @SerializedName("webcast_live"                       ) var webcastLive                    : Boolean?               = null,
    @SerializedName("program"                            ) var program                        : ArrayList<Program>      = arrayListOf(),
    @SerializedName("orbital_launch_attempt_count"       ) var orbitalLaunchAttemptCount      : Int?                   = null,
    @SerializedName("location_launch_attempt_count"      ) var locationLaunchAttemptCount     : Int?                   = null,
    @SerializedName("pad_launch_attempt_count"           ) var padLaunchAttemptCount          : Int?                   = null,
    @SerializedName("agency_launch_attempt_count"        ) var agencyLaunchAttemptCount       : Int?                   = null,
    @SerializedName("orbital_launch_attempt_count_year"  ) var orbitalLaunchAttemptCountYear  : Int?                   = null,
    @SerializedName("location_launch_attempt_count_year" ) var locationLaunchAttemptCountYear : Int?                   = null,
    @SerializedName("pad_launch_attempt_count_year"      ) var padLaunchAttemptCountYear      : Int?                   = null,
    @SerializedName("agency_launch_attempt_count_year"   ) var agencyLaunchAttemptCountYear   : Int?                   = null

) : Parcelable