package com.kltn.ecodemy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Other1
import com.kltn.ecodemy.ui.theme.Primary1

val COURSE_TAG_PADDING_HORIZONTAL = 12.dp
val COURSE_TAG_PADDING_VERTICAL = 4.dp

@Composable
fun CourseTag(
    isOnline: Boolean
) {
    lateinit var text: String
    val color: Color
    if (isOnline) {
        text = "Online"
        color = Other1
    } else {
        text = stringResource(id = R.string.course_tag_center_based)
        color = Primary1
    }

    Box(
        modifier = Modifier
            .clip(Constant.BORDER_SHAPE)
            .background(color = color)
            .padding(
                horizontal = COURSE_TAG_PADDING_HORIZONTAL,
                vertical = COURSE_TAG_PADDING_VERTICAL
            )
    ) {
        Text(
            text = text,
            style = Nunito.Subtitle2.textStyle,
            color = Neutral1,
        )
    }
}

@Preview
@Composable
fun CourseTagPrev() {
    CourseTag(isOnline = true)
}

@Preview
@Composable
fun CourseTagPrev1() {
    CourseTag(isOnline = false)
}