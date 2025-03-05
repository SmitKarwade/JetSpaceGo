package com.example.jetspacego.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Agency(
    @SerializedName("abbrev") @Expose val abbrev: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("response_mode") @Expose val responseMode: String,
    @SerializedName("type") @Expose val type: TypeXX,
    @SerializedName("url") @Expose val url: String
)