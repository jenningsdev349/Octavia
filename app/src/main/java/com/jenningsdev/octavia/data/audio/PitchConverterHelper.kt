package com.jenningsdev.octavia.data.audio

import kotlin.math.log2
import kotlin.math.roundToInt

object PitchConverterHelper {

    fun hzToNoteName(freq: Float): String {
        val midi = hzToMidi(freq)
        return midiToNote(midi)
    }

    private fun hzToMidi(freq: Float): Int =
        (69 + 12 * log2(freq / 440f)).roundToInt()

    private fun midiToNote(midi: Int): String {
        val names = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
        val octave = midi / 12 - 1
        return "${names[midi % 12]}$octave"
    }
}