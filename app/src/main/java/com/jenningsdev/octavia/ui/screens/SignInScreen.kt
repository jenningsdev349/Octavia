package com.jenningsdev.octavia.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.data.model.auth.SignInState

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error -> Toast.makeText(
            context,
            error,
            Toast.LENGTH_LONG)
            .show()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onSignInClick) {
            Text(
                stringResource(R.string.sign_in_button)
            )
        }
    }
}