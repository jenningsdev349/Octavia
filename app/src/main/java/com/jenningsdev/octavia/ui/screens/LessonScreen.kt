package com.jenningsdev.octavia.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun LessonScreen(
    note: String?,
    pitchHz: Float,
    startAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val audioPermission = Manifest.permission.RECORD_AUDIO

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startAudio()
        }
    }

    LaunchedEffect(true) {
        if (ContextCompat.checkSelfPermission(context, audioPermission)
            != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(audioPermission)
        } else {
            startAudio()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Note: $note", fontSize = 48.sp)
        Spacer(Modifier.height(20.dp))
        Text("${"%.2f".format(pitchHz)} Hz", fontSize = 24.sp)
    }

}