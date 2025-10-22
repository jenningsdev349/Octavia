package com.jenningsdev.octavia.ui.navigation

sealed class NavRoutes(val route: String) {
    data object splashScreen : NavRoutes("splash_screen")
    data object signUp : NavRoutes("sign_up")
    data object signIn : NavRoutes("sign_in")
}