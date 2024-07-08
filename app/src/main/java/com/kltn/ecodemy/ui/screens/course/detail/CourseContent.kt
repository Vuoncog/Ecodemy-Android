package com.kltn.ecodemy.ui.screens.course.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.CourseLessonSection
import com.kltn.ecodemy.ui.theme.StartLinear

private fun Modifier.imageSize(): Modifier {
    return this then Modifier
        .height(180.dp)
        .fillMaxWidth()
        .clip(Constant.BORDER_SHAPE)
}

private val IMAGE_HORIZONTAL_PADDING = 16.dp

@Composable
fun CourseContent(
    paddingValues: PaddingValues,
    course: Course,
    reviews: List<Review>,
    userReview: Review?,
    firstCourse: Boolean?,
    isLogout: Boolean,
    onLoginClicked: () -> Unit,
    onPurchaseClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onWishlistClicked: () -> Unit,
    onRatedClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
    onChatToConsultant: () -> Unit,
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(course.poster)
        .error(R.drawable.course)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()

    val onCheck = remember {
        mutableIntStateOf(1)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            CourseHeaderInfo(
                onClickBack = onBackClicked,
            )
        }
        item {
            Image(
                painter = rememberAsyncImagePainter(model = imageRequest),
                contentDescription = "Course Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(StartLinear)
                    .padding(
                        horizontal = IMAGE_HORIZONTAL_PADDING
                    )
                    .padding(bottom = 12.dp)
                    .imageSize(),
                contentScale = ContentScale.Crop
            )
        }

        item {
            CourseTeacherInfo(
                teacher = course.teacher.userInfo
            )
            if (course.type) {
                CourseOnlinePrice(
                    price = course.price,
                    salePrice = course.salePrice,
                    wishlistStatus = course.wishlistStatus,
                    purchaseStatus = course.purchaseStatus,
                    isLogout = isLogout,
                    onPurchaseClicked = onPurchaseClicked,
                    onWishlistClicked = onWishlistClicked,
                    onLoginClicked = onLoginClicked,
                )
            } else {
                CourseOfflinePrice(
                    price = course.price,
                    salePrice = course.salePrice,
                    isLogout = isLogout,
                    onLoginClicked = onLoginClicked,
                    wishlistStatus = course.wishlistStatus,
                    purchaseStatus = course.purchaseStatus,
                    onRegisterClicked = onRegisterClicked,
                    onWishlistClicked = onWishlistClicked,
                    onContactClicked = onChatToConsultant
                )
            }
            when (firstCourse) {
                null -> {
                    LoginRequiredText()
                }

                true -> {
                    UpgradeText()
                }

                else -> {}
            }
        }

        item {
            CourseSelectionTab(
                onCheck = onCheck
            )
        }

        courseInfo(
            course = course,
            paddingValues = paddingValues,
            onCheck = onCheck,
            reviews = reviews,
            userReview = userReview,
            onLessonItemClicked = onLessonItemClicked,
            onRatedClicked = onRatedClicked
        )
    }
}

fun LazyListScope.courseInfo(
    paddingValues: PaddingValues,
    onCheck: MutableState<Int>,
    course: Course,
    reviews: List<Review>,
    userReview: Review?,
    onRatedClicked: () -> Unit,
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
) {
    when (onCheck.value) {
        2 -> {
            item {
                if (!course.type) {
                    CourseOfflineLessonHeader(
                        totalMembers = "${course.about.offCourse.classSize} people",
                        totalLesson = course.about.allCourse.lecture,
                        schedule = course.about.offCourse.schedule
                    )
                }
            }
            items(course.lecture.sections.size) {
                val section = course.lecture.sections[it]
                CourseLessonSection(
                    paddingValues = if (it == course.lecture.sections.size - 1) paddingValues else PaddingValues(),
                    sectionName = section.title,
                    lessons = section.lessons,
                    purchaseStatus = course.purchaseStatus,
                    onLessonItemClicked = onLessonItemClicked,
                    sectionIndex = it,
                )
            }
        }

        3 -> {
            if (course.purchaseStatus) {
                item {
                    RateCourse(
                        isRated = userReview != null,
                        onRateClicked = onRatedClicked
                    )
                }
            }
            items(reviews.size) {
                CourseTabReviewItem(
                    review = reviews[it],
                    paddingValues = if (it == reviews.size - 1) paddingValues else PaddingValues()
                )
            }
        }

        else -> item {
            CourseTabAbout(
                aboutContent = course.about.allCourse.about,
                lectureContent = course.about.allCourse.lecture,
                prerequisiteContent = course.about.allCourse.prerequisite,
                outputContent = course.about.allCourse.output,
                paddingValues = paddingValues
            )
        }
    }
}