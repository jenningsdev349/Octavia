package com.jenningsdev.octavia.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.data.model.models.Gesture
import com.jenningsdev.octavia.ui.navigation.NavRoutes

@Composable
fun LessonScreen(
    lessonId: Int,
    navigationEvent: String?,
    navController: NavController,
    gesture: State<Gesture>,
    note: String?,
    startAudio: () -> Unit,
    isNoteCorrect: Boolean,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (lessonId) {
        1 -> CameraAndNoteLessonScreen(
            note = note,
            startAudio = startAudio,
            isNoteCorrect = isNoteCorrect,
            onNextClick = onNextClick,
            gesture = gesture,
            modifier = modifier
        )
    }

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "lessonList" -> {
                navController.navigate(NavRoutes.lessonList.route)
            }
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Column() {
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            }, modifier = modifier
        )
    }
}

@Composable
fun PhotoReviewScreen(
    bitmap: Bitmap,
    isNoteCorrect: Boolean,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.photo_taken_label), fontSize = 24.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isNoteCorrect) Text(stringResource(R.string.correct_note_label)) else Text(
                stringResource(R.string.incorrect_note_label)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Button(
                onClick = { onNextClick() },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.next_button))
            }
        }
    }
}

@Composable
fun CameraAndNoteLessonScreen(
    note: String?,
    gesture: State<Gesture>,
    startAudio: () -> Unit,
    isNoteCorrect: Boolean,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val audioPermission = Manifest.permission.RECORD_AUDIO

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startAudio()
        }
    }

    LaunchedEffect(true) {
        if (ContextCompat.checkSelfPermission(
                context, cameraPermission
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context, audioPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(cameraPermission)
            launcher.launch(audioPermission)
        } else {
            startAudio()
        }
    }

    if (capturedBitmap == null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        R.string.perform_gesture_and_note_label, gesture.value.gestureName
                    ), fontSize = 24.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Note: $note", fontSize = 24.sp)
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isNoteCorrect) {
                    Text(stringResource(R.string.correct_label), fontSize = 24.sp)
                } else {
                    Text(stringResource(R.string.incorrect_label), fontSize = 24.sp)
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CameraPreview(
                    controller = controller, modifier = modifier.fillMaxSize()
                )

                Button(
                    onClick = {
                        takePhoto(
                            controller = controller, onPhotoTaken = { bitmap ->
                                capturedBitmap = bitmap
                            }, context = context
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(stringResource(R.string.capture_photo_button))
                }
            }
        }
    } else {
        PhotoReviewScreen(
            bitmap = capturedBitmap!!,
            isNoteCorrect = isNoteCorrect,
            onNextClick = onNextClick,
            modifier = modifier.fillMaxSize()
        )
    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit,
    context: Context
) {
    controller.takePicture(ContextCompat.getMainExecutor(context),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                onPhotoTaken(image.toBitmap())
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
            }
        })
}