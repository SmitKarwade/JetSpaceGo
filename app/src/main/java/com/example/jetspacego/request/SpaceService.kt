package com.example.jetspacego.request

import com.example.jetspacego.model.launches.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceService {
    @GET("launches")
    suspend fun getMissions(
        @Query("ordering") ordering: String = "-net",
        @Query("lsp__name") search: String?,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int
    ): MissionResponse
}