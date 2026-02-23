package com.jenningsdev.octavia.data.model.models

import com.jenningsdev.octavia.R

data class Gesture(
    val video: Int,
    val gestureName: String,
    val majorNote: String,
    val minorNote: String
) {
    companion object {
        private val Do = Gesture(video = R.raw.test, gestureName = "Do", majorNote = "C", minorNote = "C")
        private val Re = Gesture(video = R.raw.test, gestureName = "Re", majorNote = "D", minorNote = "D")
        private val Mi = Gesture(video = R.raw.test, gestureName = "Mi", majorNote = "E", minorNote = "D#")
        private val Fa = Gesture(video = R.raw.test, gestureName = "Fa", majorNote = "F", minorNote = "F")
        private val So = Gesture(video = R.raw.test, gestureName = "So", majorNote = "G", minorNote = "G")
        private val La = Gesture(video = R.raw.test, gestureName = "La", majorNote = "A", minorNote = "G#")
        private val Ti = Gesture(video = R.raw.test, gestureName = "Ti", majorNote = "B", minorNote = "A#")

        private val allGestures = listOf(Do, Re, Mi, Fa, So, La, Ti)

        fun random(): Gesture {
            return allGestures.random()
        }
    }
}
