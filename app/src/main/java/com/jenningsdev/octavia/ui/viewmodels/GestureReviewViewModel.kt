package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GestureReviewViewModel: ViewModel() {
    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    private val _reviewItems = listOf("Could be better!", "I did okay!", "I did great!")
    val reviewItems = _reviewItems

    fun onNextClick() {
        _navigationEvent.value = NavRoutes.lessonList.route
    }
}