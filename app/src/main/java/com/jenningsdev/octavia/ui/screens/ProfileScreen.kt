package com.jenningsdev.octavia.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.ui.navigation.NavRoutes

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit,
    navController: NavController,
    username: String?,
    navigationEvent: String?
) {
    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "splash_screen" -> {
                navController.navigate(NavRoutes.splashScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
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
                    painter = painterResource(R.drawable.user),
                    contentDescription = stringResource(R.string.logo_content_description),
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
                Text(
                    text = username.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour2)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.view_analytics_button))
                }

                OutlinedButton(
                    onSignOutClick,
                    border = BorderStroke(2.dp, colorResource(R.color.colour3)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colorResource(R.color.colour3)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.sign_out_button))
                }
            }
        }
    }

}