package com.jenningsdev.octavia.data.repositories

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val context: Context
) {
    private val database: DatabaseReference = Firebase.database.reference
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "null"

    private val prefs =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    suspend fun getLessonsComplete(): Int {
        val lessonsComplete: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonsComplete")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonsComplete ?: 0
    }

    suspend fun updateLessonsComplete() {
        val lessonComplete: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonsComplete")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonComplete != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonsComplete")
                .setValue(lessonComplete + 1)
        }
    }

    suspend fun getLessonStatGestureCorrect(): Int {
        val lessonStatOkay: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatGestureCorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatOkay ?: 0
    }

    suspend fun updateLessonStatGestureCorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatGestureCorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatGestureCorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatGestureIncorrect(): Int {
        val lessonStatGreat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatGestureIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatGreat ?: 0
    }

    suspend fun updateLessonStatGestureIncorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatGestureIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatGestureIncorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatNoteCorrect(): Int {
        val lessonStatNoteCorrect: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatNoteCorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatNoteCorrect ?: 0
    }

    suspend fun updateLessonStatNoteCorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatNoteCorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatNoteCorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatNoteIncorrect(): Int {
        val lessonStatNoteIncorrect: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatNoteIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatNoteIncorrect ?: 0
    }

    suspend fun updateLessonStatNoteIncorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatNoteIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatNoteIncorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatIntervalCorrect(): Int {
        val lessonStatIntervalCorrect: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatIntervalCorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatIntervalCorrect ?: 0
    }

    suspend fun updateLessonStatIntervalCorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatIntervalCorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatIntervalCorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatIntervalIncorrect(): Int {
        val lessonStatIntervalIncorrect: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatIntervalIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatIntervalIncorrect ?: 0
    }

    suspend fun updateLessonStatIntervalIncorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatIntervalIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatIntervalIncorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatEarTrainingCorrect(): Int {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatEarTrainingCorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStat ?: 0
    }

    suspend fun updateLessonStatEarTrainingCorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatEarTrainingCorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatEarTrainingCorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatEarTrainingIncorrect(): Int {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatEarTrainingIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStat ?: 0
    }

    suspend fun updateLessonStatEarTrainingIncorrect() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("userStats")
            .child("lessonStatEarTrainingIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("userStats")
                .child("lessonStatEarTrainingIncorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getCurrentUserName(): String? {
        val username: String? = database
            .child("users")
            .child(uid)
            .child("name")
            .get()
            .await()
            .getValue(String::class.java)
        return username
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}