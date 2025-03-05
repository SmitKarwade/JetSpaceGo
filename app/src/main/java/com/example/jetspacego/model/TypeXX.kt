package com.example.jetspacego.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TypeXX(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String
)