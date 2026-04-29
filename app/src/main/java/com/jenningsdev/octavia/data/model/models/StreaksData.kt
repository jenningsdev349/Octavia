package com.jenningsdev.octavia.data.model.models

import java.time.LocalDate

data class StreaksData(
    val previousDate: Long,
    val streakDays: Int
) {
    companion object {
        fun checkStreak(streaksData: StreaksData): Boolean {
            val currentDate = LocalDate.now().toEpochDay()
            return streaksData.previousDate + 1 == currentDate
        }
        fun checkDayDifference(streaksData: StreaksData) : Boolean {
            val currentDate = LocalDate.now().toEpochDay()
            return currentDate >= streaksData.previousDate + 2
        }
    }
}
