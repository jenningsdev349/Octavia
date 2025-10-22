package com.jenningsdev.octavia.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jenningsdev.octavia.R

@Composable
fun SplashScreen(
    navController: NavController,
    navigationEvent: String?,
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "sign_in" -> {
                navController.navigate("sign_in")
            }
            "sign_up" -> {
                navController.navigate("sign_up")
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.piano),
                    contentDescription = stringResource(R.string.logo_content_description),
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
                Text(
                    text = stringResource(R.string.app_title),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.placeholder_text),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onLogInClick,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour2)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.splash_screen_log_in_button))
                }

                OutlinedButton(
                    onClick = onSignUpClick,
                    border = BorderStroke(2.dp, colorResource(R.color.colour3)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colorResource(R.color.colour3)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.splash_screen_sign_up_button))
                }
            }
        }
    }
}


@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        navController = NavController(LocalContext.current),
        navigationEvent = "",
        onLogInClick = {},
        onSignUpClick = {}
    )
}