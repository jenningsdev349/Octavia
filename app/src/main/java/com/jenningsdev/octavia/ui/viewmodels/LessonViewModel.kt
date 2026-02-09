package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenningsdev.octavia.data.audio.AudioEngine
import com.jenningsdev.octavia.data.model.models.Gesture
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LessonViewModel : ViewModel() {
    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    private val audioEngine = AudioEngine()

    private val _note = MutableStateFlow("No note detected!")
    val note: StateFlow<String> = _note

    private val _pitchHz = MutableStateFlow(0f)
    val pitchHz: StateFlow<Float> = _pitchHz

    private val _gesture = MutableStateFlow(Gesture.random())
    val gesture: StateFlow<Gesture> = _gesture

    fun startAudio() {
        viewModelScope.launch {
            audioEngine.startAudio { hz, note ->
                _pitchHz.value = hz
                _note.value = note
            }
        }
    }

    fun isNoteCorrect(): Boolean {
        return _note.value == _gesture.value.note
    }

    override fun onCleared() {
        audioEngine.stopAudio()
        super.onCleared()
    }

    fun onNextClick() {
        _navigationEvent.value = NavRoutes.gestureReview.route
    }
}