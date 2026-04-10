package com.jenningsdev.octavia.data.model.models

data class User(
    val email: String,
    val name: String,
    val lessonsComplete: Int,
    val lessonStatGestureCorrect: Int,
    val lessonStatGestureIncorrect: Int,
    val lessonStatNoteCorrect: Int,
    val lessonStatNoteIncorrect: Int,
    val lessonStatIntervalCorrect: Int,
    val lessonStatIntervalIncorrect: Int,
    val points: Int
)
