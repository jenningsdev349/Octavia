package com.jenningsdev.octavia.ui.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.ui.navigation.DashboardNavGraph
import com.jenningsdev.octavia.ui.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navigationEvent: String?,
    onBottomNavSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "dashboard" -> {
                navController.navigate("dashboard")
            }
            "lessonList" -> {
                navController.navigate("lessonList")
            }
            "profile" -> {
                navController.navigate("profile")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dashboard_app_bar)) },
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onItemClicked = { route ->
                    onBottomNavSelected(route)
                }
            )
        }
    ) { innerPadding ->
        DashboardNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    onItemClicked: (String) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorResource(R.color.colour4)
    ) {
        NavigationBarItem(
            selected = currentRoute == NavRoutes.dashboard.route,
            icon = {
                Icon(
                    painterResource(id = R.drawable.home),
                    contentDescription = stringResource(R.string.home_content_description)
                )
            },
            label = { Text(stringResource(R.string.home_icon_label)) },
            onClick = { onItemClicked(NavRoutes.dashboard.route) },
            modifier = Modifier
                .height(24.dp)
        )

        NavigationBarItem(
            selected = currentRoute == NavRoutes.lessonList.route,
            icon = {
                Icon(
                    painterResource(id = R.drawable.musical_note),
                    contentDescription = stringResource(R.string.lessons_content_description)
                )
            },
            label = { Text(stringResource(R.string.lessons_icon_label)) },
            onClick = { onItemClicked(NavRoutes.lessonList.route) },
            modifier = Modifier.height(24.dp)
        )

        NavigationBarItem(
            selected = currentRoute == NavRoutes.profile.route,
            icon = {
                Icon(
                    painterResource(id = R.drawable.user),
                    contentDescription = stringResource(R.string.profile_content_description)
                )
            },
            label = { Text(stringResource(R.string.profile_icon_label)) },
            onClick = { onItemClicked(NavRoutes.profile.route) },
            modifier = Modifier.height(24.dp)
        )
    }
}

