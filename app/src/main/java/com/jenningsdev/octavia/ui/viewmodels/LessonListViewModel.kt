package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jenningsdev.octavia.data.model.models.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LessonListViewModel : ViewModel() {
    private val _lessons = MutableStateFlow(
        listOf(
            Lesson(
                lessonId = 1,
                title = "Kodály Method Lesson 1: Major Scale",
                description = "Learn about Major Scale through performing gestures and singing corresponding note"
            ),
            Lesson(
                lessonId = 2,
                title = "Kodály Method Lesson 2: Minor Scale",
                description = "Learn about Minor Scale through performing gestures and singing corresponding note"
            ),
            Lesson(
                lessonId = 3,
                title = "Kodály Method Lesson 3: Note Intervals",
                description = "Learn about intervals between notes and note relationships."
            ),
            Lesson(
                lessonId = 4,
                title = "Kodály Method Lesson 4: Note Intervals + Gestures",
                description = "Learn about and perform gestures corresponding to intervals between notes and note relationships."
            ),
            Lesson(
                lessonId = 5,
                title = "Kodály Method Lesson 5: Ear Training",
                description = "Listen along to note being played, select correct note from list."
            ),
            Lesson(
                lessonId = 6,
                title = "Kodály Method Lesson 6: Ear Training + Gestures",
                description = "Listen along to note being played, select correct note and perform correct gesture."
            )
        )
    )
    val lessons: StateFlow<List<Lesson>> = _lessons

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent
}