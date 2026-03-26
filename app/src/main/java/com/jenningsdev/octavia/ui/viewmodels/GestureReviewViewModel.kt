package com.jenningsdev.octavia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jenningsdev.octavia.data.model.models.GestureRating
import com.jenningsdev.octavia.data.repositories.UserRepository
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GestureReviewViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository: UserRepository =
        UserRepository(context = application.applicationContext)

    private var database: DatabaseReference = Firebase.database.reference

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    private val _reviewItems = listOf(GestureRating.couldBeBetter, GestureRating.itWasOkay, GestureRating.itWasGreat)
    val reviewItems = _reviewItems

    fun updateLessonStatOkay() {
        viewModelScope.launch {
            userRepository.updateLessonStatOkay()
        }
    }

    fun updateLessonStatBetter() {
        viewModelScope.launch {
            userRepository.updateLessonStatBetter()
        }
    }

    fun updateLessonStatGreat() {
        viewModelScope.launch {
            userRepository.updateLessonStatGreat()
        }
    }

    fun onNextClick() {
        _navigationEvent.value = NavRoutes.lessonList.route
    }
}