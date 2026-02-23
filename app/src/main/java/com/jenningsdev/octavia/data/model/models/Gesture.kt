package com.jenningsdev.octavia.data.model.models

import com.jenningsdev.octavia.R

data class Gesture(
    val video: Int,
    val gestureName: String,
    val majorNote: String,
    val minorNote: String
) {
    companion object {
        private val Do = Gesture(video = R.raw.do_gesture, gestureName = "Do", majorNote = "C", minorNote = "C")
        private val Re = Gesture(video = R.raw.re_gesture, gestureName = "Re", majorNote = "D", minorNote = "D")
        private val Mi = Gesture(video = R.raw.mi_gesture, gestureName = "Mi", majorNote = "E", minorNote = "D#")
        private val Fa = Gesture(video = R.raw.fa_gesture, gestureName = "Fa", majorNote = "F", minorNote = "F")
        private val So = Gesture(video = R.raw.so_gesture, gestureName = "So", majorNote = "G", minorNote = "G")
        private val La = Gesture(video = R.raw.la_gesture, gestureName = "La", majorNote = "A", minorNote = "G#")
        private val Ti = Gesture(video = R.raw.ti_gesture, gestureName = "Ti", majorNote = "B", minorNote = "A#")

        private val allGestures = listOf(Do, Re, Mi, Fa, So, La, Ti)

        fun random(): Gesture {
            return allGestures.random()
        }
    }
}
