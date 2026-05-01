package com.jenningsdev.octavia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jenningsdev.octavia.data.model.auth.SignInState
import com.jenningsdev.octavia.data.model.models.LessonData
import com.jenningsdev.octavia.data.model.models.StreaksData
import com.jenningsdev.octavia.data.model.models.User
import com.jenningsdev.octavia.data.model.models.UserGrade
import com.jenningsdev.octavia.data.model.models.UserStats
import com.jenningsdev.octavia.data.repositories.AuthRepository
import com.jenningsdev.octavia.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val authRepository: AuthRepository = AuthRepository()
    val userRepository: UserRepository =
        UserRepository(context = application.applicationContext)

    private var database: DatabaseReference = Firebase.database.reference

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    fun onSignInClick(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.update {
                it.copy(
                    isSignInSuccessful = false,
                    signInError = "Email or password cannot be empty"
                )
            }
        }

        authRepository.signIn(email, password) { success, error ->
            if (success) {
                userRepository.setLoggedIn(true)
                _uiState.update { it.copy(isSignInSuccessful = true) }
                _navigationEvent.value = "dashboard"
            } else {
                _uiState.update {
                    it.copy(
                        isSignInSuccessful = false,
                        signInError = "Authentication failed"
                    )
                }

            }
        }
    }

    fun onSignUpClick(email: String, password: String, name: String) {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            _uiState.update {
                it.copy(
                    isSignInSuccessful = false,
                    signInError = "Email, name and password cannot be empty"
                )
            }
            return
        }

        authRepository.signUp(email, password) { success, error ->
            if (success) {
                val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
                val user = firebaseAuth.currentUser
                val uid = user?.uid

                userRepository.setLoggedIn(true)
                _uiState.update { it.copy(isSignInSuccessful = true, signInError = null) }
                createNewUser(uid, email, name)
                _navigationEvent.value = "dashboard"
            } else {
                _uiState.update {
                    it.copy(
                        isSignInSuccessful = false,
                        signInError = error ?: "Sign up failed"
                    )
                }
            }
        }
    }

    private fun createNewUser(uid: String?, email: String, name: String) {
        val user = User(
            email,
            name,
            userStats = UserStats(
                lessonStatGestureCorrect = 0,
                lessonStatGestureIncorrect = 0,
                lessonStatNoteCorrect = 0,
                lessonStatNoteIncorrect = 0,
                lessonStatIntervalCorrect = 0,
                lessonStatIntervalIncorrect = 0,
                lessonStatEarTrainingCorrect = 0,
                lessonStatEarTrainingIncorrect = 0,
            ),
            streaksData = StreaksData(
                previousDate = LocalDate.now().toEpochDay(),
                streakDays = 0
            ),
            lessonData = LessonData(
                lessonsComplete = 0,
                successRate = 0
            ),
            userGrade = UserGrade(
                userGrade = "",
                points = 0
            )
        )
        if (uid != null) {
            database.child("users").child(uid).setValue(user)
        }
    }
}