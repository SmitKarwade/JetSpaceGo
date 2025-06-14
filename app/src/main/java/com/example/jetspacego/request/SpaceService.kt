package com.example.jetspacego.request

import com.example.jetspacego.model.launches.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceService {
    @GET("launches")
    suspend fun getMissions(
        @Query("ordering") ordering: String = "-net",
        @Query("window_start__gt") windowStartAfter: String? = null,
        @Query("window_end__lt") windowEndBefore: String? = null,
        @Query("lsp__name") search: String?,
        @Query("limit") limit: Int = 11,
        @Query("offset") offset: Int
    ): MissionResponse
}