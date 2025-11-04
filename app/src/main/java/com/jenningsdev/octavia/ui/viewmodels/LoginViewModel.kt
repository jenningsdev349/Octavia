package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jenningsdev.octavia.data.model.auth.SignInState
import com.jenningsdev.octavia.data.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    val repository: AuthRepository = AuthRepository()

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    fun onSignInClick(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(
                    isSignInSuccessful = false,
                    signInError = "Email or password cannot be empty"
                )
            }
        }

        repository.signIn(email, password) { success, error ->
            if (success) {
                _uiState.update { it.copy(isSignInSuccessful = true) }
                _navigationEvent.value = "dashboard"
            } else {
                _uiState.update {
                    it.copy(
                        isSignInSuccessful = false,
                        signInError = "Authentication failed"
                    )
                }

            }
        }
    }

    fun onSignUpClick(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(
                    isSignInSuccessful = false,
                    signInError = "Email or password cannot be empty"
                )
            }
            return
        }

        repository.signUp(email, password) { success, error ->
            if (success) {
                _uiState.update { it.copy(isSignInSuccessful = true, signInError = null) }
                _navigationEvent.value = "dashboard"
            } else {
                _uiState.update {
                    it.copy(
                        isSignInSuccessful = false,
                        signInError = error ?: "Sign up failed"
                    )
                }
            }
        }
    }
}