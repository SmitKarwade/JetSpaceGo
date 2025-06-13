package com.example.jetspacego.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetspacego.constants.Constants
import com.example.jetspacego.model.tts.AudioConfig
import com.example.jetspacego.model.tts.Input
import com.example.jetspacego.model.tts.TTSInput
import com.example.jetspacego.model.tts.Voice
import com.example.jetspacego.request.TTSService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketViewModel @Inject constructor(
    private val ttsService: TTSService
) : ViewModel() {

    private val _audioBase64 = MutableStateFlow<String?>(null)
    val audioBase64: StateFlow<String?> get() = _audioBase64

    fun getRocketAudio(rocketDescription: String) = viewModelScope.launch {
        try {
            val request = TTSInput(
                input = Input(rocketDescription),
                voice = Voice(),
                audioConfig = AudioConfig()
            )

            val response = ttsService.synthesizeSpeech(request)
            _audioBase64.value = response.audioContent
        } catch (e: Exception) {
            e.printStackTrace()
            _audioBase64.value = null
        }
    }

    fun clearAudioBase64() {
        _audioBase64.value = null
    }
}
