package com.kltn.ecodemy.ui.screens.wishlist

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.CourseTag
import com.kltn.ecodemy.ui.components.CourseTypeTag
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

private val WISHLIST_TAG_BETWEEN_SPACE = Arrangement.spacedBy(8.dp)
private val WISHLIST_CONTENT_BETWEEN_SPACE = Arrangement.spacedBy(2.dp)
private val WISHLIST_COURSE_IMAGE_SHAPE = RoundedCornerShape(6.dp)
private val WISHLIST_COURSE_IMAGE_SIZE = 56.dp

private fun Modifier.imageSize(): Modifier {
    return this then Modifier
        .size(WISHLIST_COURSE_IMAGE_SIZE)
        .clip(WISHLIST_COURSE_IMAGE_SHAPE)
}

private fun Modifier.cardSize(): Modifier {
    return this then Modifier
        .fillMaxWidth()
}

@Composable
fun WishlistCourseCard(
    context: Context,
    course: Course,
    onCardClicked: (String) -> Unit,
    onRemoveClicked: (String) -> Unit,
) {
    val imageRequest = ImageRequest.Builder(context)
        .data(course.poster)
        .error(R.drawable.combo_course)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .clickable { onCardClicked(course._id) }
            .padding(
                bottom = 4.dp
            )
            .background(Color.White)
            .cardSize()
            .padding(
                horizontal = PADDING_SCREEN,
                vertical = 8.dp
            )
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
            verticalArrangement = WISHLIST_CONTENT_BETWEEN_SPACE
        ) {
            Row(
                horizontalArrangement = WISHLIST_TAG_BETWEEN_SPACE
            ) {
                CourseTag(isOnline = course.type)
                course.category.forEach {
                    CourseTypeTag(text = it)
                }
            }
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
            Column(
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                EcodemyText(
                    data = "$ ${course.price}",
                    format = Nunito.Subtitle1,
                    color = Neutral1
                )
            }
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                WishlistRemoveButton(
                    textContent = stringResource(id = R.string.wishlist_remove),
                    onClick = {
                        onRemoveClicked(course._id)
                    }
                )
            }
        }
    }
}
//@Preview
//@Composable
//fun WLCourseCardPrev() {
//    WishlistCourseCard(
//        courseTitle = "Build Kotlin Multiplatform Mobile Apps for iOS and Android",
//        courseTeacherName = "Stevdza-san",
//        onRemoveClicked ={}
//    )
//}