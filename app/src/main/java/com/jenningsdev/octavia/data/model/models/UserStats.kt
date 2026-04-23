package com.jenningsdev.octavia.data.model.models

data class UserStats(
    val lessonsComplete: Int,
    val lessonStatGestureCorrect: Int,
    val lessonStatGestureIncorrect: Int,
    val lessonStatNoteCorrect: Int,
    val lessonStatNoteIncorrect: Int,
    val lessonStatIntervalCorrect: Int,
    val lessonStatIntervalIncorrect: Int,
    val lessonStatEarTrainingCorrect: Int,
    val lessonStatEarTrainingIncorrect: Int,
)
