package com.jenningsdev.octavia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jenningsdev.octavia.ui.screens.HomeScreen
import com.jenningsdev.octavia.ui.screens.LessonListScreen
import com.jenningsdev.octavia.ui.viewmodels.LessonListViewModel

@Composable
fun DashboardNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.dashboard.route,
        modifier = modifier
    ) {
        composable(NavRoutes.dashboard.route) {
            HomeScreen()
        }
        composable(NavRoutes.lessonList.route) {
            val viewModel = viewModel<LessonListViewModel>()
            val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
            val lessons by viewModel.lessons.collectAsStateWithLifecycle()
            LessonListScreen(
                lessons = lessons,
                navController = navController,
                navigationEvent = navigationEvent,
            )
        }
        composable(NavRoutes.profile.route) {
            HomeScreen()
        }
    }
}