package com.jenningsdev.octavia.data.model.models

data class Gesture(
    val gestureName: String,
    val majorNote: String,
    val minorNote: String
) {
    companion object {
        private val Do = Gesture(gestureName = "Do", majorNote = "C", minorNote = "C")
        private val Re = Gesture(gestureName = "Re", majorNote = "D", minorNote = "D")
        private val Mi = Gesture(gestureName = "Mi", majorNote = "E", minorNote = "D#")
        private val Fa = Gesture(gestureName = "Fa", majorNote = "F", minorNote = "F")
        private val So = Gesture(gestureName = "So", majorNote = "G", minorNote = "G")
        private val La = Gesture(gestureName = "La", majorNote = "A", minorNote = "G#")
        private val Ti = Gesture(gestureName = "Ti", majorNote = "B", minorNote = "A#")

        private val allGestures = listOf(Do, Re, Mi, Fa, So, La, Ti)

        fun random(): Gesture {
            return allGestures.random()
        }
    }
}
