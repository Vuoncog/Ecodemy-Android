package com.kltn.ecodemy.ui.screens.search

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.EMPTY_STAR
import com.kltn.ecodemy.constant.Constant.FILLED_STAR
import com.kltn.ecodemy.constant.Constant.HALF_STAR
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.user.UserInfo
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.CourseTag
import com.kltn.ecodemy.ui.components.CourseTypeTag
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.RatingText
import com.kltn.ecodemy.ui.theme.Warning

private val SEARCH_LIST_COURSE_PADDING = PaddingValues(
    start = Constant.PADDING_SCREEN,
    end = Constant.PADDING_SCREEN,
    top = 12.dp,
    bottom = 8.dp
)
private val SEARCH_LIST_COURSE_SPACE_BETWEEN = Arrangement.spacedBy(4.dp)
private val SEARCH_COURSE_ICON_SIZE = 12.dp

private val SEARCH_COURSE_IMAGE_SHAPE = RoundedCornerShape(6.dp)
private val SEARCH_COURSE_IMAGE_SIZE = 56.dp
private fun Modifier.imageSize(): Modifier {
    return this then Modifier
        .size(SEARCH_COURSE_IMAGE_SIZE)
        .clip(SEARCH_COURSE_IMAGE_SHAPE)
}

@ExperimentalLayoutApi
fun LazyListScope.searchListCourses(
    keyword: String,
    listCourses: List<Course>,
    onCardClicked: (String) -> Unit,
) {
    item {
        EcodemyText(
            format = Nunito.Subtitle1,
            data = stringResource(id = R.string.search_result) + " '$keyword'",
            color = Neutral2,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = PADDING_SCREEN)
                .padding(top = 12.dp)
        )
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.courses_title),
            color = Neutral1,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = PADDING_SCREEN)
                .padding(bottom = 4.dp)
        )
    }
    items(listCourses) { course ->
        SearchCourseInfo(
            course = course,
            onCardClicked = onCardClicked
        )
        Box(
            modifier = Modifier
                .background(Color.White)
                .height(4.dp)
                .fillMaxWidth()
        )
    }
}

@ExperimentalLayoutApi
@Composable
fun SearchCourseInfo(
    course: Course,
    onCardClicked: (String) -> Unit,
    context: Context = LocalContext.current
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .data(course.poster)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onCardClicked(course._id) }
            .padding(
                vertical = 8.dp,
                horizontal = PADDING_SCREEN
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Course Image",
            modifier = Modifier
                .imageSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CourseTag(isOnline = course.type)
                course.category.forEach {
                    CourseTypeTag(text = it)
                }
            }
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
            CourseRating(rating = course.rate, totalReview = course.rateCount)
            EcodemyText(
                format = Nunito.Title1,
                data = "$ ${course.price}",
                color = Neutral1
            )
        }
    }
}

@Composable
fun CourseRating(
    rating: Double,
    totalReview: Int? = null,
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
                modifier = Modifier.size(SEARCH_COURSE_ICON_SIZE),
                tint = Warning
            )
        }
        repeat(halfStar ?: 0) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.star_half),
                contentDescription = "Half star",
                modifier = Modifier.size(SEARCH_COURSE_ICON_SIZE),
                tint = Warning
            )
        }
        repeat(emptyStar ?: 0) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.star_outlined),
                contentDescription = "Empty star",
                modifier = Modifier.size(SEARCH_COURSE_ICON_SIZE),
                tint = Neutral3
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        if (totalReview != null) {
            EcodemyText(
                format = Nunito.Subtitle2,
                data = "($totalReview)",
                color = Neutral3
            )
        }
    }
}

@Composable
fun Double.calculateRating(): Map<String, Int> {
    var filledStars by remember { mutableIntStateOf(0) }
    var halfFilledStars by remember { mutableIntStateOf(0) }
    var emptyStars by remember { mutableIntStateOf(0) }
    val value = this
    LaunchedEffect(key1 = this) {
        if (value in 0.0..5.0) {
            val (firstNumber, lastNumber) = value.toString()
                .split(".").map { it.toInt() }
            when (lastNumber) {
                0 -> {
                    filledStars += firstNumber
                    emptyStars = 5 - (filledStars + halfFilledStars)
                }

                in 1..5 -> {
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