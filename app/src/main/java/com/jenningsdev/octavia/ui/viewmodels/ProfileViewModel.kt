package com.jenningsdev.octavia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jenningsdev.octavia.data.repositories.AuthRepository
import com.jenningsdev.octavia.data.repositories.UserRepository
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository: UserRepository =
        UserRepository(application.applicationContext)
    val authRepository: AuthRepository = AuthRepository()

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    suspend fun getUsername(): String {
        return userRepository.getCurrentUserName()
    }

    suspend fun getUserGrade(): String {
        return userRepository.getUserGrade()
    }

    suspend fun updateUserGrade() {
        return userRepository.updateUserGrade()
    }

    fun onSignOutClick() {
        authRepository.signOut()
        userRepository.setLoggedIn(false)
        _navigationEvent.value = NavRoutes.splashScreen.route
    }
}