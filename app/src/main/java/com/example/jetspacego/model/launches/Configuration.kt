package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Configuration (

    @SerializedName("response_mode" ) var responseMode : String?             = null,
    @SerializedName("id"            ) var id           : Int?                = null,
    @SerializedName("url"           ) var url          : String?             = null,
    @SerializedName("name"          ) var name         : String?             = null,
    @SerializedName("families"      ) var families     : ArrayList<Families> = arrayListOf(),
    @SerializedName("full_name"     ) var fullName     : String?             = null,
    @SerializedName("variant"       ) var variant      : String?             = null

) : Parcelable