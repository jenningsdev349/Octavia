package com.jenningsdev.octavia.ui.screens

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.data.model.auth.SignInState

@Composable
fun SignUpScreen(
    state: SignInState,
    navController: NavController,
    navigationEvent: String?,
    onSignUpClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error -> Toast.makeText(
            context,
            error,
            Toast.LENGTH_LONG)
            .show()
        }
    }

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "dashboard" -> {
                navController.navigate("dashboard")
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                .padding(horizontal = 24.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.email_label)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password_label)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(
                onClick = { onSignUpClick(email, password) },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour2)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.sign_up_button))
            }
        }
    }
}