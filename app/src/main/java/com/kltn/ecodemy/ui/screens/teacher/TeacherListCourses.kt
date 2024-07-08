package com.kltn.ecodemy.ui.screens.teacher

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.EMPTY_STAR
import com.kltn.ecodemy.constant.Constant.FILLED_STAR
import com.kltn.ecodemy.constant.Constant.HALF_STAR
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.RatingText
import com.kltn.ecodemy.ui.theme.Warning

private val TEACHER_LIST_COURSE_PADDING = PaddingValues(
    start = Constant.PADDING_SCREEN,
    end = Constant.PADDING_SCREEN,
    top = 12.dp,
    bottom = 8.dp
)
private val TEACHER_LIST_COURSE_SPACE_BETWEEN = Arrangement.spacedBy(8.dp)
private val TEACHER_COURSE_THUMBNAIL_SIZE = 72.dp
private val TEACHER_COURSE_ICON_SIZE = 12.dp

@Composable
fun TeacherListCourses(
    listCourses: List<Course>,
    teacherName: String,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(TEACHER_LIST_COURSE_PADDING),
        verticalArrangement = TEACHER_LIST_COURSE_SPACE_BETWEEN,
    ) {
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.courses_title),
            color = Neutral1
        )
        Log.d("vuoncog", "${listCourses.isEmpty()}")
        if (listCourses.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                listCourses.forEach { course: Course ->
                    TeacherCourseInfo(course = course)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                EcodemyText(
                    format = Nunito.Subtitle1,
                    data = stringResource(R.string.teacher_empty_course, teacherName),
                    color = Neutral3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
            }
        }
    }
}

@Composable
fun TeacherCourseInfo(
    modifier: Modifier = Modifier,
    course: Course,
    context: Context = LocalContext.current,
    onItemClicked: (String) -> Unit = {},
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .data(course.poster)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .then(modifier)
            .clickableWithoutRippleEffect(true) {
                onItemClicked(course._id)
            }
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Teacher profile",
            modifier = Modifier
                .size(TEACHER_COURSE_THUMBNAIL_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            EcodemyText(
                format = Nunito.Title1,
                data = course.title,
                color = Neutral1
            )
            EcodemyText(
                format = Nunito.Subtitle3,
                data = course.teacher.userInfo.fullName,
                color = Neutral2
            )
            TeacherCourseRating(rating = course.rate, totalReview = course.rateCount)
            EcodemyText(
                format = Nunito.Title1,
                data = "$ ${course.price}",
                color = Neutral1
            )
        }
    }
}

@Composable
fun TeacherCourseRating(
    rating: Double,
    totalReview: Int,
) {
    val star = rating.calculateRating()
    val fillStar = star[FILLED_STAR]
    val halfStar = star[HALF_STAR]
    val emptyStar = star[EMPTY_STAR]

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        EcodemyText(
            format = Nunito.Subtitle1,
            data = rating.toString(),
            color = RatingText
        )
        Spacer(modifier = Modifier.width(4.dp))
        repeat(fillStar ?: 0) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.star),
                contentDescription = "Star",
                modifier = Modifier.size(TEACHER_COURSE_ICON_SIZE),
                tint = Warning
            )
        }
        repeat(halfStar ?: 0) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.star_half),
                contentDescription = "Half star",
                modifier = Modifier.size(TEACHER_COURSE_ICON_SIZE),
                tint = Warning
            )
        }
        repeat(emptyStar ?: 0) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.star_outlined),
                contentDescription = "Empty star",
                modifier = Modifier.size(TEACHER_COURSE_ICON_SIZE),
                tint = Neutral3
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        EcodemyText(
            format = Nunito.Subtitle2,
            data = "($totalReview)",
            color = Neutral3
        )
    }
}

@Composable
fun Double.calculateRating(): Map<String, Int> {
    var filledStars by remember { mutableIntStateOf(0) }
    var halfFilledStars by remember { mutableIntStateOf(0) }
    var emptyStars by remember { mutableIntStateOf(0) }
    val value = this
    LaunchedEffect(key1 = this) {
        if (value in 0.1..5.0) {
            val (firstNumber, lastNumber) = value.toString()
                .split(".").map { it.toInt() }
            if (firstNumber == 5){
                filledStars += firstNumber
                return@LaunchedEffect
            }
            when (lastNumber) {
                in 0..5 -> {
                    filledStars += firstNumber
                    halfFilledStars++
                    emptyStars = 5 - (filledStars + halfFilledStars)
                }

                else -> {
                    filledStars += firstNumber + 1
                    emptyStars = 5 - (filledStars + halfFilledStars)
                }
            }
        } else {
            emptyStars = 5
        }
    }

    return mapOf(
        FILLED_STAR to filledStars,
        HALF_STAR to halfFilledStars,
        EMPTY_STAR to emptyStars
    )
}