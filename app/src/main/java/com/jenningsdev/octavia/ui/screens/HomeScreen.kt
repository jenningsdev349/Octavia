package com.jenningsdev.octavia.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jenningsdev.octavia.R
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries

@Composable
fun HomeScreen(
    lessonsComplete: Int,
    lessonStatBetter: Int,
    lessonStatOkay: Int,
    lessonStatGreat: Int,
    lessonStatNoteCorrect: Int,
    lessonStatNoteIncorrect: Int,
    lessonStatIntervalCorrect: Int,
    lessonStatIntervalIncorrect: Int,
    modifier: Modifier = Modifier
) {
    val gestureLessonData = listOf(lessonStatBetter, lessonStatOkay, lessonStatGreat)
    val noteLessonData = listOf(lessonStatNoteCorrect, lessonStatNoteIncorrect)
    val intervalLessonData = listOf(lessonStatIntervalCorrect, lessonStatIntervalIncorrect)

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.piano),
                    contentDescription = "Octavia",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        StreaksCard(
            title = "You currently have 7 days logged in a row!",
            subtitle = "Complete a lesson daily to continue your streak.",
            image = painterResource(id = R.drawable.fire),
        )

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
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

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    stringResource(R.string.gesture_lesson_stats_label),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                BarChart(
                    data = gestureLessonData,
                    labels = listOf("Meh", "Okay", "Great!")
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    stringResource(R.string.note_lesson_stats_label),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                BarChart(
                    data = noteLessonData,
                    labels = listOf("Correct", "Incorrect")

                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    stringResource(R.string.interval_lesson_stats_label),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                BarChart(
                    data = intervalLessonData,
                    labels = listOf("Correct", "Incorrect")
                )
            }
        }
    }
}

@Composable
fun BarChart(
    data: List<Int>,
    labels: List<String>
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(data) {
        modelProducer.runTransaction {
            columnSeries {
                series(data)
            }
        }
    }

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = { _, x, _ ->
                    labels.getOrNull(x.toInt()) ?: ""
                }
            ),
        ),
        modelProducer = modelProducer,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

@Composable
fun StreaksCard(
    title: String,
    subtitle: String,
    image: Painter,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.colour6)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
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
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

