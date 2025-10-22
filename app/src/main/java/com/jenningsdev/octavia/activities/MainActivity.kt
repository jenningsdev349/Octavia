package com.jenningsdev.octavia.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.ui.GoogleAuthUiClient
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import com.jenningsdev.octavia.ui.screens.SignInScreen
import com.jenningsdev.octavia.ui.screens.SplashScreen
import com.jenningsdev.octavia.ui.theme.OctaviaTheme
import com.jenningsdev.octavia.ui.viewmodels.SignInViewModel
import com.jenningsdev.octavia.ui.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
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
                        val viewModel = viewModel<SignInViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        viewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.sign_in_toast),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        SignInScreen(
                            state = state,
                            onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signInAuth()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
