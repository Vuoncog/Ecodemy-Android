package com.kltn.ecodemy.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.screens.course.detail.RateCourse
import com.kltn.ecodemy.ui.screens.search.CourseRating
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary3


private val COURSE_SPACE_BETWEEN = Arrangement.spacedBy(32.dp)
private val COURSE_CARD_VERTICAL_PADDING = PaddingValues(top = 12.dp, bottom = 8.dp)

@Composable
fun PopularCourseCardList(
    title: String,
    onCardClicked: (String) -> Unit,
    courses: List<Course>,
    context: Context
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(horizontal = Constant.PADDING_SCREEN)
            .padding(COURSE_CARD_VERTICAL_PADDING),
        verticalArrangement = Constant.ITEM_SPACE,
    ) {
        EcodemyText(
            data = title,
            format = Nunito.Heading2,
            color = Neutral1
        )
        LazyRow(
            horizontalArrangement = COURSE_SPACE_BETWEEN,
//            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            items(courses) {
                CourseRatingCard(
                    context = context,
                    course = it,
                    onCardClicked = onCardClicked
                )
            }
        }
    }
}

@Composable
fun CourseCardList(
    title: String,
    onCardClicked: (String) -> Unit,
    courses: List<Course>,
    context: Context
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(horizontal = Constant.PADDING_SCREEN)
            .padding(COURSE_CARD_VERTICAL_PADDING),
        verticalArrangement = Constant.ITEM_SPACE,
    ) {
        EcodemyText(
            data = title,
            format = Nunito.Heading2,
            color = Neutral1
        )
        LazyRow(
            horizontalArrangement = COURSE_SPACE_BETWEEN,
//            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            items(courses) {
                CourseCard(
                    context = context,
                    course = it,
                    onCardClicked = onCardClicked
                )
            }
        }
    }
}

@Composable
fun CourseCardListForLatestCourse(
    title: String,
    latestCourse: String,
    onCardClicked: (String) -> Unit,
    courses: List<Course>,
    context: Context
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = Constant.PADDING_SCREEN)
            .padding(COURSE_CARD_VERTICAL_PADDING),
        verticalArrangement = Constant.ITEM_SPACE,
    ) {
        EcodemyAnnotatedText(
            data = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Neutral1
                    )
                ) {
                    append("$title ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Primary3
                    )
                ) {
                    append("[${latestCourse.trim()}]".trim())
                }
            },
            format = Nunito.Heading2,
        )
        LazyRow(
            horizontalArrangement = COURSE_SPACE_BETWEEN,
//            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            items(courses) {
                CourseCard(
                    context = context,
                    course = it,
                    onCardClicked = onCardClicked
                )
            }
        }
    }
}

private fun Modifier.imageSize(): Modifier {
    return this then Modifier
        .height(140.dp)
        .fillMaxWidth()
        .clip(Constant.BORDER_SHAPE)
}

private fun Modifier.cardSize(): Modifier {
    return this then Modifier
        .width(248.dp)
}

private val COURSE_TYPE_HORIZONTAL_PADDING = Arrangement.spacedBy(12.dp)
private val COURSE_TYPE_VERTICAL_PADDING = Arrangement.spacedBy(8.dp)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CourseCard(
    context: Context,
    course: Course,
    onCardClicked: (String) -> Unit
) {
    val imageRequest = ImageRequest.Builder(context)
        .data(course.poster)
        .error(R.drawable.course)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()

    Column(
        modifier = Modifier
            .cardSize()
            .clickable { onCardClicked(course._id) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Course Image",
            modifier = Modifier
                .imageSize()
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            EcodemyText(
                data = course.title,
                format = Nunito.Title1,
                color = Neutral1
            )
            EcodemyText(
                data = course.teacher.userInfo.fullName,
                format = Nunito.Subtitle1,
                color = Neutral2
            )
            FlowRow(
                horizontalArrangement = COURSE_TYPE_HORIZONTAL_PADDING,
                verticalArrangement = COURSE_TYPE_VERTICAL_PADDING,
                modifier = Modifier.padding(
                    top = 2.dp,
                    bottom = 8.dp
                )
            ) {
                CourseTag(isOnline = course.type)
                course.category.forEach {
                    CourseTypeTag(text = it)
                }
            }
            if (course.salePrice == 0.0) {
                EcodemyText(
                    data = "$ ${course.price}",
                    format = Nunito.Title1,
                    color = Neutral1
                )
            } else {
                EcodemyText(
                    data = "$ ${course.price}",
                    format = Nunito.Subtitle2,
                    color = Neutral2,
                    textDecoration = TextDecoration.LineThrough,
                )
                EcodemyText(
                    data = "$ ${course.salePrice}",
                    format = Nunito.Title1,
                    color = Neutral1
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CourseRatingCard(
    context: Context,
    course: Course,
    onCardClicked: (String) -> Unit
) {
    val imageRequest = ImageRequest.Builder(context)
        .data(course.poster)
        .error(R.drawable.course)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()

    Column(
        modifier = Modifier
            .cardSize()
            .clickable { onCardClicked(course._id) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Course Image",
            modifier = Modifier
                .imageSize()
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            EcodemyText(
                data = course.title,
                format = Nunito.Title1,
                color = Neutral1
            )
            EcodemyText(
                data = course.teacher.userInfo.fullName,
                format = Nunito.Subtitle1,
                color = Neutral2
            )
            CourseRating(rating = course.rate, totalReview = course.rateCount)
            FlowRow(
                horizontalArrangement = COURSE_TYPE_HORIZONTAL_PADDING,
                verticalArrangement = COURSE_TYPE_VERTICAL_PADDING,
                modifier = Modifier.padding(
                    top = 2.dp,
                    bottom = 8.dp
                )
            ) {
                CourseTag(isOnline = course.type)
                course.category.forEach {
                    CourseTypeTag(text = it)
                }
            }
            if (course.salePrice == 0.0) {
                EcodemyText(
                    data = "$ ${course.price}",
                    format = Nunito.Title1,
                    color = Neutral1
                )
            } else {
                EcodemyText(
                    data = "$ ${course.price}",
                    format = Nunito.Subtitle2,
                    color = Neutral2,
                    textDecoration = TextDecoration.LineThrough,
                )
                EcodemyText(
                    data = "$ ${course.salePrice}",
                    format = Nunito.Title1,
                    color = Neutral1
                )
            }
        }
    }
}