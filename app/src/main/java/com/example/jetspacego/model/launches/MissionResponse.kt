package com.example.jetspacego.model.launches

import com.google.gson.annotations.SerializedName


data class MissionResponse (

    @SerializedName("count"    ) var count    : Int?               = null,
    @SerializedName("next"     ) var next     : String?            = null,
    @SerializedName("previous" ) var previous : String?            = null,
    @SerializedName("results"  ) var results  : ArrayList<Results> = arrayListOf()

)