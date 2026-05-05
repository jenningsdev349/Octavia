package com.jenningsdev.octavia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import be.tarsos.dsp.util.PitchConverter
import com.jenningsdev.octavia.data.audio.AudioEngine
import com.jenningsdev.octavia.data.model.models.Gesture
import com.jenningsdev.octavia.data.model.models.GestureRating
import com.jenningsdev.octavia.data.model.models.NoteInterval
import com.jenningsdev.octavia.data.repositories.UserRepository
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LessonViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository =
        UserRepository(context = application.applicationContext)

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    private val audioEngine = AudioEngine()

    private val _note = MutableStateFlow("No note detected!")
    val note: StateFlow<String> = _note

    private val _pitchHz = MutableStateFlow(0f)
    val pitchHz: StateFlow<Float> = _pitchHz

    private val _gesture = MutableStateFlow(Gesture.random())
    val gesture: StateFlow<Gesture> = _gesture

    private val _noteInterval = MutableStateFlow(NoteInterval.random())
    val noteInterval = _noteInterval

    private val _firstNote = MutableStateFlow(0)
    val firstNote: StateFlow<Int> = _firstNote

    private val _secondNote = MutableStateFlow(0)
    val secondNote: StateFlow<Int> = _secondNote

    private val _reviewItems = listOf(GestureRating.correct, GestureRating.incorrect)
    val reviewItems = _reviewItems

    private val _intervalCorrect = MutableStateFlow(false)
    val isIntervalCorrect = _intervalCorrect


    private val _randomIntervals = MutableStateFlow(listOf(createRandomInterval(), createRandomInterval()))
    val randomIntervals: StateFlow<List<NoteInterval>> = _randomIntervals

    fun startAudio() {
        viewModelScope.launch {
            audioEngine.startAudio { hz, note ->
                _pitchHz.value = hz
                _note.value = note
            }
        }
    }

    fun stopAudio() {
        viewModelScope.launch {
            audioEngine.stopAudio()
        }
    }

    fun updateLessonsComplete() {
        viewModelScope.launch {
            userRepository.updateLessonsComplete()
        }
    }

    fun updatePoints(newPoints: Int) {
        viewModelScope.launch {
            userRepository.updatePoints(newPoints)
        }
    }

    fun updateLessonStatNoteCorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatNoteCorrect()
        }
    }

    fun updateLessonStatNoteIncorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatNoteIncorrect()
        }
    }

    fun updateLessonStatIntervalCorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatIntervalCorrect()
        }
    }

    fun updateLessonStatIntervalIncorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatIntervalIncorrect()
        }
    }

    fun updateLessonStatEarTrainingCorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatEarTrainingCorrect()
        }
    }

    fun updateLessonStatEarTrainingIncorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatEarTrainingIncorrect()
        }
    }

    fun isMajorNoteCorrect(): Boolean {
        return _note.value == _gesture.value.majorNote
    }

    fun isMinorNoteCorrect(): Boolean {
        return _note.value == _gesture.value.minorNote
    }

    override fun onCleared() {
        audioEngine.stopAudio()
        super.onCleared()
    }

    fun captureFirstNote() {
        _firstNote.value = PitchConverter.hertzToMidiKey(pitchHz.value.toDouble())
    }

    fun captureSecondNote() {
        _secondNote.value = PitchConverter.hertzToMidiKey(pitchHz.value.toDouble())
    }

    fun detectNoteInterval(
        expectedInterval: NoteInterval = _noteInterval.value
    ) {
        val actualInterval = _secondNote.value - _firstNote.value
        _intervalCorrect.value = actualInterval == expectedInterval.semitones
    }

    fun updateLessonStatGestureIncorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatGestureIncorrect()
        }
    }

    fun updateLessonStatGestureCorrect() {
        viewModelScope.launch {
            userRepository.updateLessonStatGestureCorrect()
        }
    }

    fun onNextClick() {
        _navigationEvent.value = NavRoutes.lessonList.route
    }

    private fun createRandomInterval(): NoteInterval {
        var randomInterval: NoteInterval
        do{
            randomInterval = NoteInterval.random()
        }
        while(randomInterval == noteInterval.value)
        return randomInterval
    }
}