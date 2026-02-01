package com.jenningsdev.octavia.data.model.models

data class Gesture(
    val gestureName: String,
    val note: String
) {
    companion object {
        private val Do = Gesture(gestureName = "Do", note = "C")
        private val Re = Gesture(gestureName = "Re", note = "D")
        private val Mi = Gesture(gestureName = "Mi", note = "E")
        private val Fa = Gesture(gestureName = "Fa", note = "F")
        private val So = Gesture(gestureName = "So", note = "G")
        private val La = Gesture(gestureName = "La", note = "A")
        private val Ti = Gesture(gestureName = "Ti", note = "B")

        private val allGestures = listOf(Do, Re, Mi, Fa, So, La, Ti)

        fun random(): Gesture {
            return allGestures.random()
        }
    }
}
