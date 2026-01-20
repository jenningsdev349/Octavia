package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenningsdev.octavia.audio.AudioEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LessonViewModel : ViewModel() {

    private val audioEngine = AudioEngine()

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note

    private val _pitchHz = MutableStateFlow(0f)
    val pitchHz: StateFlow<Float> = _pitchHz

    fun startAudio() {
        viewModelScope.launch {
            audioEngine.startAudio { hz, note ->
                _pitchHz.value = hz
                _note.value = note
            }
        }
    }

    override fun onCleared() {
        audioEngine.stopAudio()
        super.onCleared()
    }
}