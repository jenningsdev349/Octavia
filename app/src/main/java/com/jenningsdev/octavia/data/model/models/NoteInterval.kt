package com.jenningsdev.octavia.data.model.models

import com.jenningsdev.octavia.R

data class NoteInterval(
    val videoGesture1: Int,
    val videoGesture2: Int,
    val intervalName: String,
    val semitones: Int
) {
    companion object {
        val MinorSecond = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.re_gesture,
            "Minor Second",
            1
        )
        val MajorSecond = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.re_gesture,
            "Major Second",
            2
        )
        val MinorThird =
            NoteInterval(videoGesture1 = R.raw.do_gesture, R.raw.mi_gesture, "Minor Third", 3)
        val MajorThird = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.mi_gesture,
            "Major Third",
            4
        )
        val PerfectFourth = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.fa_gesture,
            "Perfect Fourth",
            5
        )
        val PerfectFifth = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.so_gesture,
            "Perfect Fifth",
            7
        )
        val MinorSixth = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.la_gesture,
            "Minor Sixth",
            8
        )
        val MajorSixth = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.la_gesture,
            "Major Sixth",
            9
        )
        val MinorSeventh = NoteInterval(
            videoGesture1 = R.raw.do_gesture,
            videoGesture2 = R.raw.ti_gesture,
            "Minor Seventh",
            10
        )
        val MajorSeventh =
            NoteInterval(
                videoGesture1 = R.raw.do_gesture,
                videoGesture2 = R.raw.ti_gesture,
                "Major Seventh",
                11
            )

        private val allIntervals = listOf(
            MinorSecond,
            MajorSecond,
            MinorThird,
            MajorThird,
            PerfectFourth,
            PerfectFifth,
            MinorSixth,
            MajorSixth,
            MinorSeventh,
            MajorSeventh
        )

        fun random(): NoteInterval {
            return allIntervals.random()
        }
    }
}
