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
    @SerializedName("gravity"                  ) var gravity                : Double?  = null,
    @SerializedName("image"                    ) var image                  : Image?   = Image(),
    @SerializedName("description"              ) var description            : String?  = null,
    @SerializedName("wiki_url"                 ) var wikiUrl                : String?  = null

) : Parcelable