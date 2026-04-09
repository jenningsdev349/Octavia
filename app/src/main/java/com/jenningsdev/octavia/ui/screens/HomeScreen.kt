package com.jenningsdev.octavia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jenningsdev.octavia.R

@Composable
fun HomeScreen(
    navController: NavController,
    navigationEvent: String?,
    lessonsComplete: Int,
    onAnalyticsClick : () -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            "analytics" -> {
                navController.navigate("analytics")
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.piano),
                    contentDescription = stringResource(R.string.octavia_content_description),
                    modifier = Modifier.size(75.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenericCard(
            title = stringResource(R.string.streaks_title_placeholder),
            subtitle = stringResource(R.string.streaks_subtitle_placeholder),
            image = painterResource(id = R.drawable.fire),
            colour = colorResource(R.color.colour6),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenericCard(
            title = stringResource(R.string.next_lesson_card_title),
            subtitle = stringResource(R.string.next_lesson_card_subtitle),
            image = painterResource(id = R.drawable.book),
            colour = colorResource(R.color.colour8),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp
        )

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.graphic),
                        contentDescription = "Analytics",
                        modifier = Modifier.size(75.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "User Analytics",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    GenericCard(
                        title = stringResource(R.string.view_stats_card_title),
                        subtitle = stringResource(R.string.view_stats_card_subtitle),
                        image = painterResource(id = R.drawable.graph),
                        colour = colorResource(R.color.colour7),
                        onClick = onAnalyticsClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.background_colour)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painterResource(R.drawable.education),
                                contentDescription = "Lesson Image",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = "$lessonsComplete",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(R.string.lessons_complete_label),
                                color = Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.background_colour)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painterResource(R.drawable.musical_note),
                                contentDescription = "Success Rate Image",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = "0%",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(R.string.overall_success_rate_label),
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenericCard(
    title: String,
    subtitle: String?,
    image: Painter,
    colour: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colour
        ),
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
