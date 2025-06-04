package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country (

    @SerializedName("id"                        ) var id                      : Int?    = null,
    @SerializedName("name"                      ) var name                    : String? = null,
    @SerializedName("alpha_2_code"              ) var alpha2Code              : String? = null,
    @SerializedName("alpha_3_code"              ) var alpha3Code              : String? = null,
    @SerializedName("nationality_name"          ) var nationalityName         : String? = null,
    @SerializedName("nationality_name_composed" ) var nationalityNameComposed : String? = null

) : Parcelable