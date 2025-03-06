package com.example.jetspacego.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @SerializedName("agencies") @Expose val agencies: List<Agency>,
    @SerializedName("description") @Expose val description: String,
    @SerializedName("end_date") @Expose val end_date: String,
    @SerializedName("id") @Expose val id: String,
    @SerializedName("image") @Expose val image: Image,
    @SerializedName("info_url") @Expose val info_url: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("start_date") @Expose val start_date: String,
    @SerializedName("type") @Expose val type: TypeXX,
    @SerializedName("wiki_url") @Expose val wiki_url: String
) :Parcelable
