package com.jenningsdev.octavia.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.data.model.models.Gesture
import com.jenningsdev.octavia.data.model.models.GestureRating
import com.jenningsdev.octavia.data.model.models.NoteInterval
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LessonScreen(
    lessonId: Int,
    navigationEvent: String?,
    navController: NavController,
    gesture: State<Gesture>,
    note: String?,
    noteInterval: State<NoteInterval>,
    startAudio: () -> Unit,
    stopAudio: () -> Unit,
    isMajorNoteCorrect: Boolean,
    isMinorNoteCorrect: Boolean,
    captureFirstNote: () -> Unit,
    captureSecondNote: () -> Unit,
    updateLessonsComplete: () -> Unit,
    updateLessonStatNoteCorrect: () -> Unit,
    updateLessonStatNoteIncorrect: () -> Unit,
    updateLessonStatIntervalCorrect: () -> Unit,
    updateLessonStatIntervalIncorrect: () -> Unit,
    updateLessonStatOkay: () -> Unit,
    updateLessonStatBetter: () -> Unit,
    updateLessonStatGreat: () -> Unit,
    reviewItems: List<GestureRating>,
    detectNoteInterval: Boolean,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedScreen by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "lessonList" -> {
                navController.navigate(NavRoutes.lessonList.route)
            }
        }
    }

    when (selectedScreen) {
        1 -> ScaleLessonScreen(
            note = note,
            startAudio = startAudio,
            stopAudio = stopAudio,
            isNoteCorrect = isMajorNoteCorrect,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatNoteCorrect = updateLessonStatNoteCorrect,
            updateLessonStatNoteIncorrect = updateLessonStatNoteIncorrect,
            updateLessonStatOkay = updateLessonStatOkay,
            updateLessonStatBetter = updateLessonStatBetter,
            updateLessonStatGreat = updateLessonStatGreat,
            reviewItems = reviewItems,
            gesture = gesture,
            modifier = modifier
        )

        2 -> ScaleLessonScreen(
            note = note,
            startAudio = startAudio,
            stopAudio = stopAudio,
            isNoteCorrect = isMinorNoteCorrect,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatNoteCorrect = updateLessonStatNoteCorrect,
            updateLessonStatNoteIncorrect = updateLessonStatNoteIncorrect,
            updateLessonStatOkay = updateLessonStatOkay,
            updateLessonStatBetter = updateLessonStatBetter,
            updateLessonStatGreat = updateLessonStatGreat,
            reviewItems = reviewItems,
            gesture = gesture,
            modifier = modifier
        )

        3 -> NoteIntervalLessonScreen(
            note = note,
            noteInterval = noteInterval,
            captureFirstNote = captureFirstNote,
            captureSecondNote = captureSecondNote,
            detectNoteInterval = detectNoteInterval,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatIntervalCorrect = updateLessonStatIntervalCorrect,
            updateLessonStatIntervalIncorrect = updateLessonStatIntervalIncorrect,
            startAudio = startAudio,
            modifier = modifier
        )

        4 -> NoteIntervalCameraLessonScreen(
            note = note,
            noteInterval = noteInterval,
            captureFirstNote = captureFirstNote,
            captureSecondNote = captureSecondNote,
            detectNoteInterval = detectNoteInterval,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatIntervalCorrect = updateLessonStatIntervalCorrect,
            updateLessonStatIntervalIncorrect = updateLessonStatIntervalIncorrect,
            updateLessonStatOkay = updateLessonStatOkay,
            updateLessonStatBetter = updateLessonStatBetter,
            updateLessonStatGreat = updateLessonStatGreat,
            reviewItems = reviewItems,
            startAudio = startAudio,
            modifier = modifier
        )

        null -> InstructionsScreen(
            lessonId = lessonId,
            onSelectScreen = { selectedScreen = it },
            gesture = gesture,
            noteInterval = noteInterval,
            modifier = modifier
        )
    }
}

@Composable
fun InstructionsScreen(
    lessonId: Int,
    onSelectScreen: (Int) -> Unit,
    gesture: State<Gesture>,
    noteInterval: State<NoteInterval>,
    modifier: Modifier = Modifier
) {
    when (lessonId) {
        1 -> ScaleInstructionsScreen(
            lessonId = lessonId,
            onSelectScreen = onSelectScreen,
            gesture = gesture,
            modifier = modifier
        )

        2 -> ScaleInstructionsScreen(
            lessonId = lessonId,
            onSelectScreen = onSelectScreen,
            gesture = gesture,
            modifier = modifier
        )

        3 -> IntervalInstructionsScreen(
            lessonId = lessonId,
            onSelectScreen = onSelectScreen,
            noteInterval = noteInterval,
            modifier = modifier
        )

        4 -> IntervalInstructionsScreen(
            lessonId = lessonId,
            onSelectScreen = onSelectScreen,
            noteInterval = noteInterval,
            modifier = modifier
        )
    }
}

@Composable
fun ScaleInstructionsScreen(
    lessonId: Int,
    onSelectScreen: (Int) -> Unit,
    gesture: State<Gesture>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.perform_gesture_label, gesture.value.gestureName),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        VideoPlayer(
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth(),
            videoId = gesture.value.video
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            stringResource(R.string.placeholder_instructions_label),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onSelectScreen(lessonId)
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.lesson_instructions_button))
        }
    }
}

@Composable
fun IntervalInstructionsScreen(
    lessonId: Int,
    noteInterval: State<NoteInterval>,
    onSelectScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Play the following Note Interval: ${noteInterval.value.intervalName}",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(156.dp))

        Text(
            stringResource(R.string.note_interval_instructions_placeholder_text),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onSelectScreen(lessonId)
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.lesson_instructions_button))
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
            },
            modifier = modifier
        )
    }
}

@Composable
fun PhotoReviewScreen(
    bitmap: Bitmap,
    gesture: State<Gesture>,
    isNoteCorrect: Boolean,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    updateLessonStatNoteCorrect: () -> Unit,
    updateLessonStatNoteIncorrect: () -> Unit,
    updateLessonStatOkay: () -> Unit,
    updateLessonStatBetter: () -> Unit,
    updateLessonStatGreat: () -> Unit,
    reviewItems: List<GestureRating>,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf(reviewItems.last()) }

    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isNoteCorrect) Text(stringResource(R.string.correct_note_label)) else Text(
                stringResource(R.string.incorrect_note_label),
                fontSize = 24.sp
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
            ) { index ->
                when (index) {
                    0 -> Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    1 -> VideoPlayer(
                        modifier = Modifier
                            .fillMaxSize(),
                        videoId = gesture.value.video
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ReviewDropdownMenu(
                    reviewItems = reviewItems,
                    selectedItem = selectedItem,
                    onItemSelected = { item ->
                        selectedItem = item
                    }
                )

                Button(
                    onClick = {
                        when (selectedItem.id) {
                            1 -> {
                                updateLessonStatBetter()
                                onNextClick()
                            }

                            2 -> {
                                updateLessonStatOkay()
                                onNextClick()
                            }

                            3 -> {
                                updateLessonStatGreat()
                                onNextClick()
                            }
                        }
                        if (isNoteCorrect) {
                            updateLessonStatNoteCorrect()
                        } else {
                            updateLessonStatNoteIncorrect()
                        }
                        updateLessonsComplete()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(stringResource(R.string.next_button))
                }
            }
        }
    }
}

@Composable
fun ScaleLessonScreen(
    note: String?,
    gesture: State<Gesture>,
    startAudio: () -> Unit,
    stopAudio: () -> Unit,
    isNoteCorrect: Boolean,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    updateLessonStatNoteCorrect: () -> Unit,
    updateLessonStatNoteIncorrect: () -> Unit,
    updateLessonStatOkay: () -> Unit,
    updateLessonStatBetter: () -> Unit,
    updateLessonStatGreat: () -> Unit,
    reviewItems: List<GestureRating>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val audioPermission = Manifest.permission.RECORD_AUDIO

    val coroutineScope = rememberCoroutineScope()

    var secondsLeft by remember { mutableStateOf(5) }
    var timerStarted by remember { mutableStateOf(false) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(capturedBitmap) {
        Log.d("BitmapDebug", "capturedBitmap updated: $capturedBitmap")
    }

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

    val borderColor by animateColorAsState(
        targetValue = if (isNoteCorrect) Color.Green else Color.Transparent
    )

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
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        R.string.perform_gesture_and_note_label, gesture.value.gestureName
                    ), fontSize = 18.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Note: $note", fontSize = 18.sp)
            }

            if (timerStarted) {
                LaunchedEffect(Unit) {
                    secondsLeft = 5
                    while (secondsLeft > 0) {
                        delay(1000)
                        secondsLeft--
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.capturing_picture_label, secondsLeft),
                        fontSize = 18.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 4.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CameraPreview(
                        controller = controller, modifier = modifier.fillMaxSize()
                    )

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                timerStarted = true

                                delay(5000L)

                                takePhoto(
                                    controller = controller, onPhotoTaken = { bitmap ->
                                        capturedBitmap = bitmap
                                    },
                                    context = context
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        if (timerStarted) {
                            Text(stringResource(R.string.capturing_photo_button))
                        } else {
                            Text(stringResource(R.string.capture_photo_button))
                        }
                    }
                }
            }
        }
    } else {
        stopAudio()

        PhotoReviewScreen(
            bitmap = capturedBitmap!!,
            gesture = gesture,
            isNoteCorrect = isNoteCorrect,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatNoteCorrect = updateLessonStatNoteCorrect,
            updateLessonStatNoteIncorrect = updateLessonStatNoteIncorrect,
            updateLessonStatOkay = updateLessonStatOkay,
            updateLessonStatBetter = updateLessonStatBetter,
            updateLessonStatGreat = updateLessonStatGreat,
            reviewItems = reviewItems,
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
fun NoteIntervalLessonScreen(
    note: String?,
    noteInterval: State<NoteInterval>,
    captureFirstNote: () -> Unit,
    captureSecondNote: () -> Unit,
    detectNoteInterval: Boolean,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    updateLessonStatIntervalCorrect: () -> Unit,
    updateLessonStatIntervalIncorrect: () -> Unit,
    startAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSecondButton by remember { mutableStateOf(false) }
    var showFinalButton by remember { mutableStateOf(false) }
    var finalButtonClicked by remember { mutableStateOf(false) }

    val audioPermission = Manifest.permission.RECORD_AUDIO
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startAudio()
        }
    }

    LaunchedEffect(true) {
        if (ContextCompat.checkSelfPermission(
                context, audioPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(audioPermission)
        } else {
            startAudio()
        }
    }

    if (!finalButtonClicked) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Play this note interval: ${noteInterval.value.intervalName}",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Note: ${note}", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(64.dp))

                if (!showSecondButton) {
                    Button(
                        onClick = {
                            captureFirstNote()
                            showSecondButton = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(stringResource(R.string.capture_first_note_button))
                    }
                } else {
                    Button(
                        onClick = {
                            captureSecondNote()
                            showFinalButton = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(stringResource(R.string.capture_second_note_button))
                    }
                }

                if (showFinalButton) {
                    Button(
                        onClick = {
                            finalButtonClicked = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(stringResource(R.string.proceed_to_next_screen_button))
                    }
                }
            }
        }
    } else {
        IntervalReviewScreen(
            isIntervalCorrect = detectNoteInterval,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatIntervalCorrect = updateLessonStatIntervalCorrect,
            updateLessonStatIntervalIncorrect = updateLessonStatIntervalIncorrect,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun NoteIntervalCameraLessonScreen(
    note: String?,
    noteInterval: State<NoteInterval>,
    captureFirstNote: () -> Unit,
    captureSecondNote: () -> Unit,
    detectNoteInterval: Boolean,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    updateLessonStatIntervalCorrect: () -> Unit,
    updateLessonStatIntervalIncorrect: () -> Unit,
    updateLessonStatOkay: () -> Unit,
    updateLessonStatBetter: () -> Unit,
    updateLessonStatGreat: () -> Unit,
    reviewItems: List<GestureRating>,
    startAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var currentPhoto by remember { mutableStateOf(1) }
    var showFinalButton by remember { mutableStateOf(false) }
    var finalButtonClicked by remember { mutableStateOf(false) }

    var secondsLeft by remember { mutableStateOf(5) }
    var timerStarted by remember { mutableStateOf(false) }

    var capturedBitmap1 by remember { mutableStateOf<Bitmap?>(null) }
    var capturedBitmap2 by remember { mutableStateOf<Bitmap?>(null) }
    val bitmaps = listOf(capturedBitmap1, capturedBitmap2)

    val audioPermission = Manifest.permission.RECORD_AUDIO
    val context = LocalContext.current

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
                context, audioPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(audioPermission)
        } else {
            startAudio()
        }
    }

    LaunchedEffect(capturedBitmap1) {
        Log.d("BitmapDebug", "capturedBitmap1 updated: $capturedBitmap1")
    }
    LaunchedEffect(capturedBitmap2) {
        Log.d("BitmapDebug", "capturedBitmap2 updated: $capturedBitmap2")
    }

    if (!finalButtonClicked) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Perform this note interval: ${noteInterval.value.intervalName}",
                    fontSize = 18.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Note: $note", fontSize = 18.sp)
            }

            if (timerStarted && secondsLeft > 0) {
                LaunchedEffect(Unit) {
                    secondsLeft = 5
                    while (secondsLeft > 0) {
                        delay(1000)
                        secondsLeft--
                    }
                    timerStarted = false
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.capturing_picture_label, secondsLeft),
                        fontSize = 18.sp
                    )
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
                        coroutineScope.launch {
                            timerStarted = true

                            delay(5000L)

                            takePhoto(
                                controller = controller, onPhotoTaken = { bitmap ->
                                    if (currentPhoto == 1) {
                                        capturedBitmap1 = bitmap
                                        captureFirstNote()
                                        currentPhoto = 2
                                    } else {
                                        capturedBitmap2 = bitmap
                                        captureSecondNote()
                                        showFinalButton = true
                                    }
                                },
                                context = context
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    if (currentPhoto == 1) Text(stringResource(R.string.capture_first_note_button)) else Text(
                        stringResource(R.string.capture_second_note_button)
                    )
                }

                if (showFinalButton) {
                    Button(
                        onClick = {
                            finalButtonClicked = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(stringResource(R.string.proceed_to_next_screen_button))
                    }
                }
            }
        }
    } else {
        IntervalReviewScreen(
            isIntervalCorrect = detectNoteInterval,
            capturedBitmaps = bitmaps,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatIntervalCorrect = updateLessonStatIntervalCorrect,
            updateLessonStatIntervalIncorrect = updateLessonStatIntervalIncorrect,
            updateLessonStatOkay = updateLessonStatOkay,
            updateLessonStatBetter = updateLessonStatBetter,
            updateLessonStatGreat = updateLessonStatGreat,
            reviewItems = reviewItems,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDropdownMenu(
    reviewItems: List<GestureRating>,
    selectedItem: GestureRating,
    onItemSelected: (GestureRating) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedItem.ratingName,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.rate_label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    colorResource(R.color.background_colour),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            reviewItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.ratingName) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun IntervalReviewScreen(
    isIntervalCorrect: Boolean,
    capturedBitmaps: List<Bitmap?>? = null,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    updateLessonStatIntervalCorrect: () -> Unit,
    updateLessonStatIntervalIncorrect: () -> Unit,
    updateLessonStatOkay: () -> Unit = {},
    updateLessonStatBetter: () -> Unit = {},
    updateLessonStatGreat: () -> Unit = {},
    reviewItems: List<GestureRating> = listOf(),
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf(reviewItems.last()) }

    val pagerState = rememberPagerState(
        pageCount = { capturedBitmaps?.size!! }
    )

    if (capturedBitmaps?.isEmpty()!!) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (isIntervalCorrect) Text(
                stringResource(R.string.note_interval_correct), modifier = Modifier.align(
                    Alignment.Center
                )
            ) else Text(
                stringResource(R.string.note_interval_incorrect), modifier = Modifier.align(
                    Alignment.Center
                )
            )

            Button(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.proceed_to_next_screen_button))
            }
        }
    } else {
        Column(
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isIntervalCorrect) Text(stringResource(R.string.note_interval_correct)) else Text(
                    stringResource(R.string.note_interval_incorrect)
                )
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { index ->
                    Image(
                        bitmap = capturedBitmaps[index]?.asImageBitmap()!!,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ReviewDropdownMenu(
                        reviewItems = reviewItems,
                        selectedItem = selectedItem,
                        onItemSelected = { item ->
                            selectedItem = item
                        }
                    )

                    Button(
                        onClick = {
                            when (selectedItem.id) {
                                1 -> {
                                    updateLessonStatBetter()
                                    onNextClick()
                                }

                                2 -> {
                                    updateLessonStatOkay()
                                    onNextClick()
                                }

                                3 -> {
                                    updateLessonStatGreat()
                                    onNextClick()
                                }
                            }
                            if (isIntervalCorrect) {
                                updateLessonStatIntervalCorrect()
                            } else {
                                updateLessonStatIntervalIncorrect()
                            }
                            updateLessonsComplete()
                            onNextClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(stringResource(R.string.next_button))
                    }
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoId: Int,
) {
    val context: Context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val uri = Uri.parse("android.resource://${context.packageName}/$videoId")
            setMediaItem(MediaItem.fromUri(uri))
            volume = 0f
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
            prepare()
        }
    }

    DisposableEffect(key1 = exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
            }
        },
        modifier = modifier
    )
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
                val rotationDegrees = image.imageInfo.rotationDegrees
                val bitmap = image.toBitmap()

                val correctedBitmap = if (rotationDegrees != 0) {
                    val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
                    Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        matrix,
                        true
                    )
                } else {
                    bitmap
                }

                image.close()
                onPhotoTaken(correctedBitmap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
            }
        })
}
