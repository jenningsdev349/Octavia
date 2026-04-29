package com.jenningsdev.octavia.data.model.models

data class User(
    val email: String,
    val name: String,
    val userStats: UserStats,
    val streaksData: StreaksData,
    val lessonData: LessonData,
    val userGrade: UserGrade,
    val leaderboardStats: LeaderboardStats
)