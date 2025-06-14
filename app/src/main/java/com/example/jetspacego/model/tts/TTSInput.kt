package com.example.jetspacego.model.tts

data class TTSInput(
    val input: Input,
    val voice: Voice,
    val audioConfig: AudioConfig
)

data class Input(val text: String)

data class Voice(
    val languageCode: String = "en-US",
    val name: String = "en-US-Neural2-D",
    val ssmlGender: String = "MALE"
)

data class AudioConfig(
    val audioEncoding: String = "MP3",
    val speakingRate: Double = 0.85,  // Slightly slow like a narrator
    val pitch: Double = -2.0          // Deeper voice
)


data class TTSResponse(val audioContent: String) // base64 encoded audio
