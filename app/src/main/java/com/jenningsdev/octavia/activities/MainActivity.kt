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
import com.jenningsdev.octavia.data.repositories.UserRepository
import com.jenningsdev.octavia.ui.navigation.NavRoutes
import com.jenningsdev.octavia.ui.screens.BottomNavigationBar
import com.jenningsdev.octavia.ui.screens.DashboardScreen
import com.jenningsdev.octavia.ui.screens.GestureReviewScreen
import com.jenningsdev.octavia.ui.screens.HomeScreen
import com.jenningsdev.octavia.ui.screens.LessonListScreen
import com.jenningsdev.octavia.ui.screens.LessonScreen
import com.jenningsdev.octavia.ui.screens.ProfileScreen
import com.jenningsdev.octavia.ui.screens.SignInScreen
import com.jenningsdev.octavia.ui.screens.SignUpScreen
import com.jenningsdev.octavia.ui.screens.SplashScreen
import com.jenningsdev.octavia.ui.theme.OctaviaTheme
import com.jenningsdev.octavia.ui.viewmodels.DashboardViewModel
import com.jenningsdev.octavia.ui.viewmodels.GestureReviewViewModel
import com.jenningsdev.octavia.ui.viewmodels.LessonListViewModel
import com.jenningsdev.octavia.ui.viewmodels.LessonViewModel
import com.jenningsdev.octavia.ui.viewmodels.LoginViewModel
import com.jenningsdev.octavia.ui.viewmodels.ProfileViewModel
import com.jenningsdev.octavia.ui.viewmodels.SplashScreenViewModel

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
                    topBar = {
                        if (currentRoute in listOf("dashboard", "lessonList", "profile")) {
                            TopAppBar(
                                title = { Text(stringResource(R.string.dashboard_app_bar)) }
                            )
                        }
                    },
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
                                HomeScreen()
                            }
                            composable(NavRoutes.lessonList.route) {
                                val viewModel = viewModel<LessonListViewModel>()
                                val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
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

                                LessonScreen(
                                    lessonId = lessonId,
                                    navigationEvent = navigationEvent,
                                    navController = navController,
                                    gesture = gesture,
                                    note = note,
                                    startAudio = { viewModel.startAudio() },
                                    isMajorNoteCorrect = viewModel.isMajorNoteCorrect(),
                                    isMinorNoteCorrect = viewModel.isMinorNoteCorrect(),
                                    stopAudio = { viewModel.stopAudio() },
                                    onNextClick = { viewModel.onNextClick() },
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                            composable(NavRoutes.gestureReview.route) {
                                val viewModel = viewModel<GestureReviewViewModel>()
                                val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
                                val reviewItems = viewModel.reviewItems

                                GestureReviewScreen(
                                    navigationEvent = navigationEvent,
                                    navController = navController,
                                    reviewItems = reviewItems,
                                    onNextClick = { viewModel.onNextClick() },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
