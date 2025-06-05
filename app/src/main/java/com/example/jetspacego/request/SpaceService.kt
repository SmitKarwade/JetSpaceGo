package com.example.jetspacego.request

import com.example.jetspacego.model.launches.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceService {
    @GET("launches")
    suspend fun getMissions(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 10
    ): MissionResponse
}