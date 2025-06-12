package com.example.jetspacego.request

import com.example.jetspacego.model.tts.TTSInput
import com.example.jetspacego.model.tts.TTSResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TTSService {
    @POST("v1/text:synthesize")
    suspend fun synthesizeSpeech(
        @Body request: TTSInput
    ): TTSResponse
}
