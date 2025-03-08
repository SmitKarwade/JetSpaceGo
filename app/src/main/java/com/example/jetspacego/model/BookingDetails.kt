package com.example.jetspacego.model

import com.google.gson.annotations.SerializedName

data class BookingDetails(
    @SerializedName("firstName") var firstName: String = "",
    @SerializedName("lastName") var lastName: String = "",
    @SerializedName("dob") var dob: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("address") var address: String = "",
    @SerializedName("nationality") var nationality: String = "",
    @SerializedName("experience") var experience: String = "",
    @SerializedName("emergencyContact") var emergencyContact: String = "",
    @SerializedName("suitSize") var suitSize: String = "",
    @SerializedName("insurance") var insurance: Boolean = false,
    @SerializedName("mealType") var mealType: String = "",
    @SerializedName("msnId") var msnId : String
)