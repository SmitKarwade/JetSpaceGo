package com.example.jetspacego.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MissionResponse(
    @SerializedName("count") @Expose val count: Int,
    @SerializedName("next") @Expose val next: String,
    @SerializedName("previous") @Expose val previous: Any,
    @SerializedName("results") @Expose val results: List<Result>
)