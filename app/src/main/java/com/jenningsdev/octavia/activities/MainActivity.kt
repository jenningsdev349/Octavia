package com.jenningsdev.octavia.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.data.model.models.StreaksData
import com.jenningsdev.octavia.data.repositories.UserRepository
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import com.jenningsdev.octavia.ui.screens.AnalyticsScreen
import com.jenningsdev.octavia.ui.screens.BottomNavigationBar
import com.jenningsdev.octavia.ui.screens.DashboardScreen
import com.jenningsdev.octavia.ui.screens.HomeScreen
import com.jenningsdev.octavia.ui.screens.LessonListScreen
import com.jenningsdev.octavia.ui.screens.LessonScreen
import com.jenningsdev.octavia.ui.screens.ProfileScreen
import com.jenningsdev.octavia.ui.screens.SignInScreen
import com.jenningsdev.octavia.ui.screens.SignUpScreen
import com.jenningsdev.octavia.ui.screens.SplashScreen
import com.jenningsdev.octavia.ui.theme.OctaviaTheme
import com.jenningsdev.octavia.ui.viewmodels.AnalyticsScreenViewModel
import com.jenningsdev.octavia.ui.viewmodels.DashboardViewModel
import com.jenningsdev.octavia.ui.viewmodels.HomeScreenViewModel
import com.jenningsdev.octavia.ui.viewmodels.LessonListViewModel
import com.jenningsdev.octavia.ui.viewmodels.LessonViewModel
import com.jenningsdev.octavia.ui.viewmodels.LoginViewModel
import com.jenningsdev.octavia.ui.viewmodels.ProfileViewModel
import com.jenningsdev.octavia.ui.viewmodels.SplashScreenViewModel
import java.time.LocalDate

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OctaviaTheme {
                val userRepository: UserRepository =
                    UserRepository(context = this)
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val startDestination =
                    if (userRepository.isLoggedIn()) {
                        "dashboard_graph"
                    } else {
                        NavRoutes.splashScreen.route
                    }
                Scaffold(
                    bottomBar = {
                        if (currentRoute in listOf("dashboard", "lessonList", "profile")) {
                            BottomNavigationBar(
                                navController = navController,
                                onItemClicked = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
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
                                onSignUpClick = { email, password, name ->
                                    viewModel.onSignUpClick(email, password, name)
                                }
                            )
                        }
                        navigation(
                            startDestination = NavRoutes.dashboard.route,
                            route = "dashboard_graph"
                        ) {
                            composable(NavRoutes.dashboard.route) {
                                val viewModel = viewModel<DashboardViewModel>()
                                val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
                                DashboardScreen(
                                    navigationEvent = navigationEvent,
                                    onBottomNavSelected = { route ->
                                        viewModel.onBottomNavSelected(route)
                                    }
                                )
                            }
                            composable(NavRoutes.dashboard.route) {
                                val viewModel = viewModel<HomeScreenViewModel>()
                                val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
                                var lessonsComplete by remember { mutableStateOf(0) }
                                var streaksDay by remember { mutableStateOf(0) }
                                var previousDate by remember { mutableStateOf(0L) }
                                var successRate by remember { mutableStateOf(0) }

                                LaunchedEffect(Unit) {
                                    lessonsComplete = viewModel.getLessonsComplete()
                                    streaksDay = viewModel.getStreaksDay()
                                    previousDate = viewModel.getStreaksDate()
                                    successRate = viewModel.calculateSuccessRate()

                                    val streaksData = StreaksData(
                                        streakDays = streaksDay,
                                        previousDate = previousDate
                                    )

                                    if(StreaksData.checkDayDifference(streaksData)) {
                                        userRepository.resetStreaksDay()
                                        userRepository.updateStreaksDate(LocalDate.now().toEpochDay())
                                        streaksDay = userRepository.getStreaksDay()
                                    }

                                    if (StreaksData.checkStreak(streaksData)) {
                                        userRepository.updateStreaksDay()
                                        userRepository.updateStreaksDate(LocalDate.now().toEpochDay())
                                        streaksDay = userRepository.getStreaksDay()
                                    }
                                }

                                HomeScreen(
                                    navController = navController,
                                    navigationEvent = navigationEvent,
                                    lessonsComplete = lessonsComplete,
                                    streaksDay = streaksDay,
                                    successRate = successRate,
                                    onAnalyticsClick = { viewModel.onAnalyticsClick() },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            composable(NavRoutes.analytics.route) {
                                val viewModel = viewModel<AnalyticsScreenViewModel>()

                                var lessonStatGestureCorrect by remember { mutableStateOf(0) }
                                var lessonStatGestureIncorrect by remember { mutableStateOf(0) }
                                var lessonStatNoteCorrect by remember { mutableStateOf(0) }
                                var lessonStatNoteIncorrect by remember { mutableStateOf(0) }
                                var lessonStatIntervalCorrect by remember { mutableStateOf(0) }
                                var lessonStatIntervalIncorrect by remember { mutableStateOf(0) }
                                var lessonStatEarTrainingCorrect by remember { mutableStateOf(0) }
                                var lessonStatEarTrainingIncorrect by remember { mutableStateOf(0) }

                                LaunchedEffect(Unit) {
                                    lessonStatGestureCorrect = viewModel.getLessonStatGestureCorrect()
                                    lessonStatGestureIncorrect = viewModel.getLessonStatGestureIncorrect()
                                    lessonStatNoteCorrect = viewModel.getLessonStatNoteCorrect()
                                    lessonStatNoteIncorrect = viewModel.getLessonStatNoteIncorrect()
                                    lessonStatIntervalCorrect = viewModel.getLessonStatIntervalCorrect()
                                    lessonStatIntervalIncorrect = viewModel.getLessonStatIntervalIncorrect()
                                    lessonStatEarTrainingCorrect = viewModel.getLessonStatEarTrainingCorrect()
                                    lessonStatEarTrainingIncorrect = viewModel.getLessonStatEarTrainingIncorrect()
                                }

                                AnalyticsScreen(
                                    lessonStatGestureCorrect = lessonStatGestureCorrect,
                                    lessonStatGestureIncorrect = lessonStatGestureIncorrect,
                                    lessonStatNoteCorrect = lessonStatNoteCorrect,
                                    lessonStatNoteIncorrect = lessonStatNoteIncorrect,
                                    lessonStatIntervalCorrect = lessonStatIntervalCorrect,
                                    lessonStatIntervalIncorrect = lessonStatIntervalIncorrect,
                                    lessonStatEarTrainingCorrect = lessonStatEarTrainingCorrect,
                                    lessonStatEarTrainingIncorrect = lessonStatEarTrainingIncorrect,
                                )
                            }
                            composable(NavRoutes.lessonList.route) {
                                val viewModel = viewModel<LessonListViewModel>()
                                val lessons by viewModel.lessons.collectAsStateWithLifecycle()
                                LessonListScreen(
                                    lessons = lessons,
                                    navController = navController
                                )
                            }
                            composable(NavRoutes.profile.route) {
                                val viewModel = viewModel<ProfileViewModel>()
                                val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
                                val uiState by viewModel.uiState.collectAsState()

                                LaunchedEffect(Unit) {
                                    viewModel.getUsername()
                                }

                                ProfileScreen(
                                    onSignOutClick = { viewModel.onSignOutClick() },
                                    navigationEvent = navigationEvent,
                                    navController = navController,
                                    username = uiState.username
                                )
                            }
                            composable(
                                route = NavRoutes.lesson.routeArg!!,
                                arguments = listOf(
                                    navArgument("lessonId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) { backStackEntry ->
                                val lessonId = backStackEntry.arguments?.getInt("lessonId") ?: 1

                                val viewModel = viewModel<LessonViewModel>()
                                val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
                                val note by viewModel.note.collectAsStateWithLifecycle()
                                val gesture = viewModel.gesture.collectAsState()
                                val noteInterval = viewModel.noteInterval.collectAsState()
                                val randomIntervals = viewModel.randomIntervals.collectAsState()
                                val reviewItems = viewModel.reviewItems

                                LessonScreen(
                                    lessonId = lessonId,
                                    navigationEvent = navigationEvent,
                                    navController = navController,
                                    gesture = gesture,
                                    note = note,
                                    noteInterval = noteInterval,
                                    randomIntervals = randomIntervals,
                                    startAudio = { viewModel.startAudio() },
                                    isMajorNoteCorrect = viewModel.isMajorNoteCorrect(),
                                    isMinorNoteCorrect = viewModel.isMinorNoteCorrect(),
                                    captureFirstNote = { viewModel.captureFirstNote() },
                                    captureSecondNote = { viewModel.captureSecondNote() },
                                    updateLessonsComplete = { viewModel.updateLessonsComplete() },
                                    updateLessonStatNoteCorrect = { viewModel.updateLessonStatNoteCorrect() },
                                    updateLessonStatNoteIncorrect = { viewModel.updateLessonStatNoteIncorrect() },
                                    updateLessonStatIntervalCorrect = { viewModel.updateLessonStatIntervalCorrect() },
                                    updateLessonStatIntervalIncorrect = { viewModel.updateLessonStatIntervalIncorrect() },
                                    updateLessonStatGestureCorrect = { viewModel.updateLessonStatGestureCorrect() },
                                    updateLessonStatGestureIncorrect = { viewModel.updateLessonStatGestureIncorrect() },
                                    updateLessonStatEarTrainingCorrect = { viewModel.updateLessonStatEarTrainingCorrect() },
                                    updateLessonStatEarTrainingIncorrect = { viewModel.updateLessonStatEarTrainingIncorrect() },
                                    reviewItems = reviewItems,
                                    detectNoteInterval = viewModel.detectNoteInterval(),
                                    stopAudio = { viewModel.stopAudio() },
                                    onNextClick = { viewModel.onNextClick() },
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
