package com.jenningsdev.octavia.data.model.models

data class GestureRating(val id: Int, val ratingName: String) {
    companion object {
        val couldBeBetter = GestureRating(1, "Could be better")
        val itWasOkay = GestureRating(2, "It was okay")
        val itWasGreat = GestureRating(3, "It was great")
    }
}