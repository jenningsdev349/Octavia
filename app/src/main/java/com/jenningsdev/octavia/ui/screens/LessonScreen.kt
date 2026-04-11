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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    randomIntervals: State<List<NoteInterval>>,
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
    updateLessonStatGestureCorrect: () -> Unit,
    updateLessonStatGestureIncorrect: () -> Unit,
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
            updateLessonStatGestureCorrect = updateLessonStatGestureCorrect,
            updateLessonStatGestureIncorrect = updateLessonStatGestureIncorrect,
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
            updateLessonStatGestureCorrect = updateLessonStatGestureCorrect,
            updateLessonStatGestureIncorrect = updateLessonStatGestureIncorrect,
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
            updateLessonStatGestureCorrect = updateLessonStatGestureCorrect,
            updateLessonStatGestureIncorrect = updateLessonStatGestureIncorrect,
            reviewItems = reviewItems,
            startAudio = startAudio,
            modifier = modifier
        )

        5 -> EarTrainingLessonScreen(
            noteInterval = noteInterval,
            randomIntervals = randomIntervals,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            modifier = modifier
        )

        6 -> EarTrainingCameraLessonScreen(
            noteInterval = noteInterval,
            updateLessonsComplete = updateLessonsComplete,
            reviewItems = reviewItems,
            onNextClick = onNextClick,
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

        5 -> EarTrainingInstructionsScreen(
            lessonId = lessonId,
            onSelectScreen = onSelectScreen,
            modifier = modifier
        )

        6 -> EarTrainingInstructionsScreen(
            lessonId = lessonId,
            onSelectScreen = onSelectScreen,
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
            stringResource(R.string.perform_gesture_label),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = gesture.value.gestureName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

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
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Play the following Note Interval: ${noteInterval.value.intervalName}",
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = noteInterval.value.intervalName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.note_interval_video_label),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            state = pagerState,
        ) { index ->
            when (index) {
                0 -> VideoPlayer(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth(),
                    videoId = noteInterval.value.videoGesture1
                )

                1 -> VideoPlayer(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth(),
                    videoId = noteInterval.value.videoGesture2
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Sing “Do” with the correct gesture. Choose any pitch, then press the button to capture. Repeat for the second note, singing the correct step away with its gesture.",
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
fun EarTrainingInstructionsScreen(
    lessonId: Int,
    onSelectScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Ear Training Lesson",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(144.dp))

        Image(
            painter = painterResource(R.drawable.hearing),
            contentDescription = "Ear Training Image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            stringResource(R.string.ear_training_instructions_text),
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
    updateLessonStatGestureCorrect: () -> Unit,
    updateLessonStatGestureIncorrect: () -> Unit,
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
            if (isNoteCorrect) Text(
                stringResource(R.string.correct_note_label),
                fontSize = 24.sp
            ) else Text(
                stringResource(R.string.incorrect_note_label),
                fontSize = 24.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Swipe right to compare!", fontSize = 24.sp)
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
                                updateLessonStatGestureCorrect()
                                onNextClick()
                            }

                            2 -> {
                                updateLessonStatGestureIncorrect()
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
    updateLessonStatGestureCorrect: () -> Unit,
    updateLessonStatGestureIncorrect: () -> Unit,
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
            updateLessonStatGestureCorrect = updateLessonStatGestureCorrect,
            updateLessonStatGestureIncorrect = updateLessonStatGestureIncorrect,
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
                    textAlign = TextAlign.Center,
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
            noteInterval = noteInterval,
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
    updateLessonStatGestureCorrect: () -> Unit,
    updateLessonStatGestureIncorrect: () -> Unit,
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

    if (!finalButtonClicked) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LaunchedEffect(timerStarted) {
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
                Text(stringResource(R.string.note_value_label, note ?: ""), fontSize = 18.sp)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                if (timerStarted)
                    Text(
                        stringResource(R.string.capturing_picture_label, secondsLeft),
                        fontSize = 18.sp
                    )
                else
                    Text(
                        stringResource(R.string.capture_picture_label),
                        fontSize = 18.sp
                    )
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

                            timerStarted = false
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
            noteInterval = noteInterval,
            isIntervalCorrect = detectNoteInterval,
            capturedBitmaps = bitmaps,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            updateLessonStatIntervalCorrect = updateLessonStatIntervalCorrect,
            updateLessonStatIntervalIncorrect = updateLessonStatIntervalIncorrect,
            updateLessonStatGestureCorrect = updateLessonStatGestureCorrect,
            updateLessonStatGestureIncorrect = updateLessonStatGestureIncorrect,
            reviewItems = reviewItems,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun EarTrainingLessonScreen(
    noteInterval: State<NoteInterval>,
    randomIntervals: State<List<NoteInterval>>,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val intervals = remember(noteInterval.value, randomIntervals.value) {
        (randomIntervals.value + noteInterval.value).shuffled()
    }
    val context = LocalContext.current

    var buttonClicked by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val uri = MediaItem.fromUri(
                "android.resource://${context.packageName}/${noteInterval.value.sound}"
            )
            setMediaItem(uri)
            prepare()
        }
    }

    LaunchedEffect(Unit) {
        exoPlayer.play()
    }

    DisposableEffect(key1 = exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    if (!buttonClicked) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(R.drawable.piano),
                contentDescription = "Octavia",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = painterResource(R.drawable.music_notes),
                contentDescription = stringResource(R.string.ear_training_content_description),
                modifier = Modifier.size(100.dp)
            )

            Text(
                stringResource(R.string.guess_interval_label),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                stringResource(R.string.what_interval_played_label),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            intervals.forEach { interval ->
                OutlinedButton(
                    onClick = {
                        if (interval == noteInterval.value) {
                            isCorrect = true
                            buttonClicked = true
                            updateLessonsComplete()
                        } else {
                            buttonClicked = true
                            updateLessonsComplete()
                        }
                    },
                    border = BorderStroke(2.dp, colorResource(R.color.colour3)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colorResource(R.color.colour3)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(interval.intervalName)
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.play_sound_button))
            }
        }
    } else {
        EarTrainingReviewScreen(
            isIntervalCorrect = isCorrect,
            noteInterval = noteInterval,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            modifier = modifier
        )
    }
}

@Composable
fun EarTrainingCameraLessonScreen(
    noteInterval: State<NoteInterval>,
    updateLessonsComplete: () -> Unit,
    reviewItems: List<GestureRating>,
    onNextClick: () -> Unit,
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

    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val uri = MediaItem.fromUri(
                "android.resource://${context.packageName}/${noteInterval.value.sound}"
            )
            setMediaItem(uri)
            prepare()
        }
    }

    LaunchedEffect(Unit) {
        exoPlayer.play()
    }

    DisposableEffect(key1 = exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }
    }

    if (!finalButtonClicked) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LaunchedEffect(timerStarted) {
                secondsLeft = 5
                while (secondsLeft > 0) {
                    delay(1000)
                    secondsLeft--
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.ear_training_label),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                if (timerStarted)
                    Text(
                        stringResource(R.string.capturing_picture_label, secondsLeft),
                        fontSize = 18.sp
                    )
                else
                    Text(
                        stringResource(R.string.capture_picture_label),
                        fontSize = 18.sp
                    )
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
                                        currentPhoto = 2
                                    } else {
                                        capturedBitmap2 = bitmap
                                        showFinalButton = true
                                    }
                                },
                                context = context
                            )

                            timerStarted = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    if (currentPhoto == 1) Text(stringResource(R.string.capture_first_photo_button)) else Text(
                        stringResource(R.string.capture_second_photo_button)
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
        EarTrainingReviewScreen(
            isIntervalCorrect = null,
            noteInterval = noteInterval,
            capturedBitmaps = bitmaps,
            onNextClick = onNextClick,
            updateLessonsComplete = updateLessonsComplete,
            reviewItems = reviewItems,
            modifier = modifier
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
    noteInterval: State<NoteInterval>,
    isIntervalCorrect: Boolean,
    capturedBitmaps: List<Bitmap?>? = null,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    updateLessonStatIntervalCorrect: () -> Unit,
    updateLessonStatIntervalIncorrect: () -> Unit,
    updateLessonStatGestureCorrect: () -> Unit = {},
    updateLessonStatGestureIncorrect: () -> Unit = {},
    reviewItems: List<GestureRating> = listOf(),
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf(reviewItems.lastOrNull()) }

    val pagerState = rememberPagerState(
        pageCount = { 4 }
    )

    if (capturedBitmaps == null) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isIntervalCorrect) Text(
                    stringResource(R.string.note_interval_correct),
                    fontSize = 18.sp
                ) else Text(
                    stringResource(R.string.note_interval_incorrect),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    painter = painterResource(R.drawable.musical_note),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                )
            }

            Button(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                modifier = Modifier
                    .fillMaxWidth()
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
                    val bitmap = capturedBitmaps.getOrNull(index)?.asImageBitmap()

                    when (index) {
                        0 -> if (bitmap != null) {
                            Image(
                                bitmap = capturedBitmaps[index]?.asImageBitmap()!!,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        1 -> if (bitmap != null) {
                            Image(
                                bitmap = capturedBitmaps[index]?.asImageBitmap()!!,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        2 -> VideoPlayer(
                            modifier = Modifier
                                .fillMaxSize(),
                            videoId = noteInterval.value.videoGesture1
                        )

                        3 -> VideoPlayer(
                            modifier = Modifier
                                .fillMaxSize(),
                            videoId = noteInterval.value.videoGesture2
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    selectedItem?.let {
                        ReviewDropdownMenu(
                            reviewItems = reviewItems,
                            selectedItem = it,
                            onItemSelected = { item ->
                                selectedItem = item
                            }
                        )
                    }

                    Button(
                        onClick = {
                            when (selectedItem?.id) {
                                1 -> {
                                    updateLessonStatGestureCorrect()
                                    onNextClick()
                                }

                                2 -> {
                                    updateLessonStatGestureIncorrect()
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
fun EarTrainingReviewScreen(
    isIntervalCorrect: Boolean? = null,
    noteInterval: State<NoteInterval>,
    capturedBitmaps: List<Bitmap?>? = null,
    onNextClick: () -> Unit,
    updateLessonsComplete: () -> Unit,
    reviewItems: List<GestureRating> = listOf(),
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf(reviewItems.lastOrNull()) }

    val pagerState = rememberPagerState(
        pageCount = { 4 }
    )

    if (capturedBitmaps.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.hearing),
                    contentDescription = "Analytics",
                    modifier = Modifier.size(75.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "Ear Training Lesson",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(144.dp))

            Image(
                painter = if (isIntervalCorrect == true) painterResource(R.drawable.check) else painterResource(
                    R.drawable.incorrect
                ),
                contentDescription = "Correct or incorrect",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (isIntervalCorrect == true) "Correct!" else "Incorrect, try again!",
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.colour5)),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.next_button))
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
                Text(
                    "Note Interval was: ${noteInterval.value.intervalName}",
                    fontSize = 18.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.swipe_right_label), fontSize = 24.sp)
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { index ->
                    val bitmap = capturedBitmaps.getOrNull(index)?.asImageBitmap()

                    when (index) {
                        0 -> if (bitmap != null) {
                            Image(
                                bitmap = capturedBitmaps[index]?.asImageBitmap()!!,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        1 -> if (bitmap != null) {
                            Image(
                                bitmap = capturedBitmaps[index]?.asImageBitmap()!!,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        2 -> VideoPlayer(
                            modifier = Modifier
                                .fillMaxSize(),
                            videoId = noteInterval.value.videoGesture1
                        )

                        3 -> VideoPlayer(
                            modifier = Modifier
                                .fillMaxSize(),
                            videoId = noteInterval.value.videoGesture2
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    selectedItem?.let {
                        ReviewDropdownMenu(
                            reviewItems = reviewItems,
                            selectedItem = it,
                            onItemSelected = { item ->
                                selectedItem = item
                            }
                        )
                    }

                    Button(
                        onClick = {
                            when (selectedItem?.id) {
                                1 -> {
                                    onNextClick()
                                }

                                2 -> {
                                    onNextClick()
                                }
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
