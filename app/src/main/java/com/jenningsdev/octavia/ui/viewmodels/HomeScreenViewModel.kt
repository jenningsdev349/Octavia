package com.jenningsdev.octavia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jenningsdev.octavia.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {
    val userRepository: UserRepository =
        UserRepository(application.applicationContext)

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    suspend fun getStreaksDay(): Int {
        return userRepository.getStreaksDay()
    }

    suspend fun getStreaksDate(): Long {
        return userRepository.getStreaksDate()
    }

    suspend fun getLessonsComplete(): Int {
        return userRepository.getLessonsComplete()
    }

    fun onAnalyticsClick() {
        _navigationEvent.value = "analytics"
    }
}