package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jenningsdev.octavia.data.model.models.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LessonListViewModel : ViewModel() {
    private val _lessons = MutableStateFlow(
        listOf(
            Lesson("lesson1","Kodály Method Lesson 1", "Learn basics of hand gestures."),
            Lesson("lesson2","Kodály Method Lesson 2", "Understanding note intervals"),
            Lesson("lesson3","Kodály Method Lesson 3", "Learn how chords are built."),
        )
    )
    val lessons: StateFlow<List<Lesson>> = _lessons

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent
}