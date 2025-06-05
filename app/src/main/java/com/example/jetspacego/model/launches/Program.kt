package com.example.jetspacego.model.launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Program (

    @SerializedName("response_mode"   ) var responseMode   : String?                   = null,
    @SerializedName("id"              ) var id             : Int?                      = null,
    @SerializedName("url"             ) var url            : String?                   = null,
    @SerializedName("name"            ) var name           : String?                   = null,
    @SerializedName("image"           ) var image          : Image?                    = Image(),
    @SerializedName("info_url"        ) var infoUrl        : String?                   = null,
    @SerializedName("wiki_url"        ) var wikiUrl        : String?                   = null,
    @SerializedName("description"     ) var description    : String?                   = null,
    @SerializedName("agencies"        ) var agencies       : ArrayList<LaunchServiceProvider>       = arrayListOf(),
    @SerializedName("start_date"      ) var startDate      : String?                   = null,
    @SerializedName("end_date"        ) var endDate        : String?                   = null,
    @SerializedName("type"            ) var type           : Type?                     = Type()

) : Parcelable