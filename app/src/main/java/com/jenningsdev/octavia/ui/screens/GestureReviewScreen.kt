package com.jenningsdev.octavia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jenningsdev.octavia.R
import com.jenningsdev.octavia.ui.navigation.NavRoutes

@Composable
fun GestureReviewScreen(
    navigationEvent: String?,
    navController: NavController,
    reviewItems: List<String>,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "lessonList" -> {
                navController.navigate(NavRoutes.lessonList.route)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = stringResource(R.string.rate_gesture_label),
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            ReviewDropdownMenu(
                reviewItems = reviewItems
            )
        }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDropdownMenu(
    reviewItems: List<String>,
    ) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(reviewItems.last()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedItem,
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
            modifier = Modifier.background(colorResource(R.color.background_colour))
        ) {
            reviewItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem = item
                        expanded = false
                    }
                )
            }
        }
    }
}