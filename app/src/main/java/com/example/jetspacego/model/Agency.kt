package com.example.jetspacego.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agency(
    @SerializedName("abbrev") @Expose val abbrev: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("type") @Expose val type: TypeXX,
    @SerializedName("url") @Expose val url: String
) :Parcelable