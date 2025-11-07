package com.jenningsdev.octavia.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import com.jenningsdev.octavia.ui.screens.DashboardScreen
import com.jenningsdev.octavia.ui.screens.SignInScreen
import com.jenningsdev.octavia.ui.screens.SignUpScreen
import com.jenningsdev.octavia.ui.screens.SplashScreen
import com.jenningsdev.octavia.ui.theme.OctaviaTheme
import com.jenningsdev.octavia.ui.viewmodels.DashboardViewModel
import com.jenningsdev.octavia.ui.viewmodels.LoginViewModel
import com.jenningsdev.octavia.ui.viewmodels.SplashScreenViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OctaviaTheme {
                val navController = rememberNavController()
                NavHost(navController, NavRoutes.splashScreen.route) {
                    composable(NavRoutes.splashScreen.route) {
                        val viewModel = viewModel<SplashScreenViewModel>()
                        val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

                        SplashScreen(
                            navController = navController,
                            navigationEvent = navigationEvent,
                            onLogInClick = { viewModel.onLogInClick() },
                            onSignUpClick = { viewModel.onSignUpClick() }
                        )
                    }
                    composable(NavRoutes.signIn.route) {
                        val viewModel = viewModel<LoginViewModel>()
                        val state by viewModel.uiState.collectAsStateWithLifecycle()
                        val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

                        SignInScreen(
                            navController = navController,
                            navigationEvent = navigationEvent,
                            state = state,
                            onSignInClick = { email, password ->
                                viewModel.onSignInClick(email, password)
                            }
                        )
                    }
                    composable(NavRoutes.signUp.route) {
                        val viewModel = viewModel<LoginViewModel>()
                        val state by viewModel.uiState.collectAsStateWithLifecycle()
                        val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()

                        SignUpScreen(
                            navController = navController,
                            navigationEvent = navigationEvent,
                            state = state,
                            onSignUpClick = { email, password ->
                                viewModel.onSignUpClick(email, password)
                            }
                        )
                    }
                    composable(NavRoutes.dashboard.route) {
                        val viewModel = viewModel<DashboardViewModel>()
                        DashboardScreen()
                    }
                }
            }
        }
    }
}
