package com.jenningsdev.octavia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jenningsdev.octavia.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnalyticsScreenViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository: UserRepository =
        UserRepository(application.applicationContext)

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    suspend fun getLessonStatBetter(): Int {
        return userRepository.getLessonStatBetter()
    }

    suspend fun getLessonStatOkay(): Int {
        return userRepository.getLessonStatOkay()
    }

    suspend fun getLessonStatGreat(): Int {
        return userRepository.getLessonStatGreat()
    }

    suspend fun getLessonStatNoteCorrect(): Int {
        return userRepository.getLessonStatNoteCorrect()
    }

    suspend fun getLessonStatNoteIncorrect(): Int {
        return userRepository.getLessonStatNoteIncorrect()
    }

    suspend fun getLessonStatIntervalCorrect(): Int {
        return userRepository.getLessonStatIntervalCorrect()
    }

    suspend fun getLessonStatIntervalIncorrect(): Int {
        return userRepository.getLessonStatIntervalIncorrect()
    }
}