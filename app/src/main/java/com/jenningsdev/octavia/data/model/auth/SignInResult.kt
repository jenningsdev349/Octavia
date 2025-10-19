package com.jenningsdev.octavia.data.model.auth

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userID: String,
    val userName: String?,
    val profilePicture: String?
)
