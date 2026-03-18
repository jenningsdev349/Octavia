package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel: ViewModel() {
    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    fun onClickAboutCard() {
        _navigationEvent.value = NavRoutes.analytics.route
    }
}