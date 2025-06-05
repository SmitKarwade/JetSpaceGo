package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Agencies (

    @SerializedName("id"                              ) var id                            : Int?               = null,
    @SerializedName("name"                            ) var name                          : String?            = null,
    @SerializedName("abbrev"                          ) var abbrev                        : String?            = null,
    @SerializedName("type"                            ) var type                          : Type?              = Type(),
    @SerializedName("featured"                        ) var featured                      : Boolean?           = null,
    @SerializedName("country"                         ) var country                       : ArrayList<Country> = arrayListOf(),
    @SerializedName("description"                     ) var description                   : String?            = null,
    @SerializedName("administrator"                   ) var administrator                 : String?            = null,
    @SerializedName("founding_year"                   ) var foundingYear                  : Int?               = null,
    @SerializedName("launchers"                       ) var launchers                     : String?            = null,
    @SerializedName("spacecraft"                      ) var spacecraft                    : String?            = null,
    @SerializedName("parent"                          ) var parent                        : String?            = null,
    @SerializedName("image"                           ) var image                         : Image?             = Image(),
    @SerializedName("logo"                            ) var logo                          : Logo?              = Logo(),
    @SerializedName("social_logo"                     ) var socialLogo                    : SocialLogo?        = SocialLogo(),
    @SerializedName("total_launch_count"              ) var totalLaunchCount              : Int?               = null,
    @SerializedName("successful_launches"             ) var successfulLaunches            : Int?               = null,
    @SerializedName("failed_launches"                 ) var failedLaunches                : Int?               = null,
    @SerializedName("successful_landings"             ) var successfulLandings            : Int?               = null,
    @SerializedName("failed_landings"                 ) var failedLandings                : Int?               = null,
    @SerializedName("successful_landings_spacecraft"  ) var successfulLandingsSpacecraft  : Int?               = null,
    @SerializedName("failed_landings_spacecraft"      ) var failedLandingsSpacecraft      : Int?               = null,
    @SerializedName("info_url"                        ) var infoUrl                       : String?            = null,
    @SerializedName("wiki_url"                        ) var wikiUrl                       : String?            = null
) : Parcelable

