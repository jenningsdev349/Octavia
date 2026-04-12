package com.jenningsdev.octavia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun AnalyticsScreen(
    lessonStatGestureCorrect: Int,
    lessonStatGestureIncorrect: Int,
    lessonStatNoteCorrect: Int,
    lessonStatNoteIncorrect: Int,
    lessonStatIntervalCorrect: Int,
    lessonStatIntervalIncorrect: Int,
    lessonStatEarTrainingCorrect: Int,
    lessonStatEarTrainingIncorrect: Int,
    modifier: Modifier = Modifier
) {
    val gestureLessonData = listOf(lessonStatGestureCorrect, lessonStatGestureIncorrect)
    val noteLessonData = listOf(lessonStatNoteCorrect, lessonStatNoteIncorrect)
    val intervalLessonData = listOf(lessonStatIntervalCorrect, lessonStatIntervalIncorrect)
    val earTrainingLessonData = listOf(lessonStatEarTrainingCorrect, lessonStatEarTrainingIncorrect)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            stringResource(R.string.gesture_lesson_stats_label),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        BarChart(
            data = gestureLessonData,
            labels = listOf("Correct", "Incorrect")
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

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            stringResource(R.string.ear_training_stats_label),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        BarChart(
            data = earTrainingLessonData,
            labels = listOf("Correct", "Incorrect")
        )
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