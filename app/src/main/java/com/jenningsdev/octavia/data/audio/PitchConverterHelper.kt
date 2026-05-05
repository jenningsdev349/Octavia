package com.jenningsdev.octavia.data.audio

import be.tarsos.dsp.util.PitchConverter

object PitchConverterHelper {
    fun hzToNoteName(freq: Float): String {
        val midi = PitchConverter.hertzToMidiKey(freq.toDouble())
        return midiToNote(midi)
    }

    private fun midiToNote(midi: Int): String {
        val names = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
        return names[midi % 12]
    }
}