package com.jenningsdev.octavia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jenningsdev.octavia.data.repositories.AuthRepository
import com.jenningsdev.octavia.data.repositories.UserRepository
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import com.jenningsdev.octavia.ui.state.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository: UserRepository =
        UserRepository(application.applicationContext)
    val authRepository: AuthRepository = AuthRepository()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    fun getUsername() {
        viewModelScope.launch {
            val name = userRepository.getCurrentUserName()
            _uiState.update {
                it.copy(
                    username = name
                )
            }
        }
    }

    fun onSignOutClick() {
        authRepository.signOut()
        userRepository.setLoggedIn(false)
        _navigationEvent.value = NavRoutes.splashScreen.route
    }
}