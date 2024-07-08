package com.kltn.ecodemy.ui.screens.learn

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

private val LEARN_COURSE_CARD_PADDING = PaddingValues(
    vertical = 8.dp,
    horizontal = 16.dp
)
private val LEARN_COURSE_IMAGE_SHAPE = RoundedCornerShape(6.dp)
private val LEARN_COURSE_IMAGE_SIZE = 56.dp
private val LEARN_PROGRESS_BAR_TRACK_COLOR = Color(0xFFF3F3F3)
private val LEARN_PROGRESS_BAR_SHAPE = RoundedCornerShape(2.dp)
private val LEARN_PROGRESS_BAR_HEIGHT = 2.dp

@Composable
fun LearnCourseCard(
    onCardClicked: (String) -> Unit,
    course: Course,
    progress: String,
    context: Context
) {
    val imageRequest = ImageRequest.Builder(context)
        .data(course.poster)
        .error(R.drawable.combo_course)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(bottom = 4.dp)
            .clickable { onCardClicked(course._id) }
            .background(Color.White)
            .fillMaxWidth()
            .padding(LEARN_COURSE_CARD_PADDING)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(LEARN_COURSE_IMAGE_SIZE)
                .clip(LEARN_COURSE_IMAGE_SHAPE),
            model = imageRequest,
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            EcodemyText(
                format = Nunito.Subtitle1,
                data = course.title,
                color = Neutral1
            )
            EcodemyText(
                format = Nunito.Subtitle2,
                data = course.teacher.userInfo.fullName,
                color = Neutral2
            )

            EcodemyText(
                format = Nunito.Subtitle3,
                data = progress,
                color = Neutral1,
                modifier = Modifier.padding(top = 10.dp)
            )
//            LearnCourseProgressBar(
//                modifier = Modifier.fillMaxWidth(),
//                progressPercentage = progress
//            )
        }
    }
}

@Composable
fun LearnCourseProgressBar(
    modifier: Modifier = Modifier,
    progressPercentage: Int,
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .then(modifier)
    ) {
        LinearProgressIndicator(
            progress = percentageToFloat(percentage = progressPercentage),
            trackColor = LEARN_PROGRESS_BAR_TRACK_COLOR,
            color = Primary1,
            modifier = Modifier
                .fillMaxWidth()
                .height(LEARN_PROGRESS_BAR_HEIGHT)
                .clip(LEARN_PROGRESS_BAR_SHAPE)
        )
        EcodemyText(
            format = Nunito.Subtitle2,
            data = stringResource(id = R.string.learn_percentage) + " ${progressPercentage}%",
            color = Neutral2
        )
    }
}

private fun percentageToFloat(percentage: Int): Float = percentage.toFloat() / 100

//@Preview
//@Composable
//fun LearnCourseCardPrev() {
//    LearnCourseCard(
//        courseTitle = "Build Kotlin Multiplatform Mobile Apps for iOS and Android",
//        courseTeacherName = "Stevdza-san",
//        progressPercentage = 10,
//        onCardClicked = {}
//    )
//}