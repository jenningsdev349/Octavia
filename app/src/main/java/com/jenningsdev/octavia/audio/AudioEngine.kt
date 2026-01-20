package com.jenningsdev.octavia.audio

import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchDetectionResult
import be.tarsos.dsp.pitch.PitchProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioEngine {
    private var dispatcher: AudioDispatcher? = null

    suspend fun startAudio(onPitch: (Float, String) -> Unit) {
        withContext(Dispatchers.Default) {
            val sampleRate = 44100
            val bufferSize = 2048
            val overlap = 1024

            dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(
                sampleRate, bufferSize, overlap
            )

            val handler = PitchDetectionHandler { result: PitchDetectionResult, _: AudioEvent ->
                val pitchHz = result.pitch
                if (pitchHz > 0) {
                    val note = PitchConverterHelper.hzToNoteName(pitchHz)
                    onPitch(pitchHz, note)
                }
            }

            dispatcher?.addAudioProcessor(
                PitchProcessor(
                    PitchProcessor.PitchEstimationAlgorithm.YIN,
                    sampleRate.toFloat(),
                    bufferSize,
                    handler
                )
            )

            Thread(dispatcher, "DSP Audio Thread").start()
        }
    }

    fun stopAudio() {
        dispatcher?.stop()
    }
}