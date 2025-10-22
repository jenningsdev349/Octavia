package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashScreenViewModel : ViewModel() {

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    fun onLogInClick() {
        _navigationEvent.value = "sign_in"
    }

    fun onSignUpClick() {
        _navigationEvent.value = "sign_up"
    }

}