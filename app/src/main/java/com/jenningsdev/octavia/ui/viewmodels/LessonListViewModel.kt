package com.jenningsdev.octavia.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.jenningsdev.octavia.data.model.models.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LessonListViewModel : ViewModel() {
    private val _lessons = MutableStateFlow(
        listOf(
            Lesson(
                "Kodály Method Lesson 1: Major Scale",
                "Learn about Major Scale through performing gestures and singing corresponding note"
            ),
            Lesson(
                "Kodály Method Lesson 2: Minor Scale",
                "Learn about Minor Scale through performing gestures and singing corresponding note"
            ),
            Lesson(
                "Kodály Method Lesson 3: Note Intervals",
                "Learn about intervals between notes and note relationships."
            ),
            Lesson(
                "Kodály Method Lesson 4: Note Intervals + Gestures",
                "Learn about and perform gestures corresponding to intervals between notes and note relationships."
            ),
            Lesson(
                "Kodály Method Lesson 5: Ear Training",
                "Listen along to note being played, select correct note from list."
            ),
            Lesson(
                "Kodály Method Lesson 6: Ear Training + Gestures",
                "Listen along to note being played, select correct note and perform correct gesture."
            ),
            Lesson(
                "Takadimi Method Lesson 1: Basic Rhythm",
                "Clap along to a 4/4 beat"
            ),
            Lesson(
                "Takadimi Method Lesson 2: Ta-Ka-Di-Mi",
                "Perform correct syllable corresponding to beat."
            )
        )
    )
    val lessons: StateFlow<List<Lesson>> = _lessons

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent
}