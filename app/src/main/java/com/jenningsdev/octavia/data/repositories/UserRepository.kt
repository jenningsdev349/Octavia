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

    private val prefs =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    suspend fun fetchCurrentUserName(): String? {

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return "null"

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