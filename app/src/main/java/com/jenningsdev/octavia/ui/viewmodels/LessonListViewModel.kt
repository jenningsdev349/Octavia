package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jenningsdev.octavia.data.model.models.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LessonListViewModel : ViewModel() {
    private val _lessons = MutableStateFlow(
        listOf(
            Lesson("Kodály Method Lesson 1: Major Scale", "Learn about Major Scale through performing gestures and singing corresponding note")
        )
    )
    val lessons: StateFlow<List<Lesson>> = _lessons

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent
}