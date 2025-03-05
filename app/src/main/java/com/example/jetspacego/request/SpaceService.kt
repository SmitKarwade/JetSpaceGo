package com.example.jetspacego.request

import com.example.jetspacego.model.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceService {
    @GET("programs")
    suspend fun getMissions(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): MissionResponse
}