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
            .child("lessonsComplete")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonComplete != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonsComplete")
                .setValue(lessonComplete + 1)
        }
    }

    suspend fun getLessonStatOkay(): Int {
        val lessonStatOkay: Int? = database
            .child("users")
            .child(uid)
            .child("lessonStatOkay")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatOkay ?: 0
    }

    suspend fun updateLessonStatOkay() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("lessonStatOkay")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonStatOkay")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatBetter(): Int {
        val lessonStatBetter: Int? = database
            .child("users")
            .child(uid)
            .child("lessonStatBetter")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatBetter ?: 0
    }

    suspend fun updateLessonStatBetter() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("lessonStatBetter")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonStatBetter")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatGreat(): Int {
        val lessonStatGreat: Int? = database
            .child("users")
            .child(uid)
            .child("lessonStatGreat")
            .get()
            .await()
            .getValue(Int::class.java)
        return lessonStatGreat ?: 0
    }

    suspend fun updateLessonStatGreat() {
        val lessonStat: Int? = database
            .child("users")
            .child(uid)
            .child("lessonStatGreat")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonStatGreat")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatNoteCorrect(): Int {
        val lessonStatNoteCorrect: Int? = database
            .child("users")
            .child(uid)
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
            .child("lessonStatNoteCorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonStatNoteCorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatNoteIncorrect(): Int {
        val lessonStatNoteIncorrect: Int? = database
            .child("users")
            .child(uid)
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
            .child("lessonStatNoteIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonStatNoteIncorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatIntervalCorrect(): Int {
        val lessonStatIntervalCorrect: Int? = database
            .child("users")
            .child(uid)
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
            .child("lessonStatIntervalCorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonStatIntervalCorrect")
                .setValue(lessonStat + 1)
        }
    }

    suspend fun getLessonStatIntervalIncorrect(): Int {
        val lessonStatIntervalIncorrect: Int? = database
            .child("users")
            .child(uid)
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
            .child("lessonStatIntervalIncorrect")
            .get()
            .await()
            .getValue(Int::class.java)

        if (lessonStat != null) {
            database
                .child("users")
                .child(uid)
                .child("lessonStatIntervalIncorrect")
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