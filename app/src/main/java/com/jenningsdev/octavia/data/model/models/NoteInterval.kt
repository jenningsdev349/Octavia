package com.jenningsdev.octavia.data.model.models

data class NoteInterval(
    val intervalName: String,
    val semitones: Int
) {
    companion object {
        val Unison = NoteInterval(intervalName = "Unison", 0)
        val MinorSecond = NoteInterval("Minor Second", 1)
        val MajorSecond = NoteInterval("Major Second", 2)
        val MinorThird = NoteInterval("Minor Third", 3)
        val MajorThird = NoteInterval("Major Third", 4)
        val PerfectFourth = NoteInterval("Perfect Fourth", 5)
        val Tritone = NoteInterval("Tritone", 6)
        val PerfectFifth = NoteInterval("Perfect Fifth", 7)
        val MinorSixth = NoteInterval("Minor Sixth", 8)
        val MajorSixth = NoteInterval("Major Sixth", 9)
        val MinorSeventh = NoteInterval("Minor Seventh", 10)
        val MajorSeventh = NoteInterval("Major Seventh", 11)

        private val allIntervals = listOf(
            Unison,
            MinorSecond,
            MajorSecond,
            MinorThird,
            MajorThird,
            PerfectFourth,
            Tritone,
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
