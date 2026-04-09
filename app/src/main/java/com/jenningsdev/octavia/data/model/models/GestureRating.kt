package com.jenningsdev.octavia.data.model.models

data class GestureRating(val id: Int, val ratingName: String) {
    companion object {
        val correct = GestureRating(1, "Correct")
        val incorrect = GestureRating(2, "Incorrect")
    }
}