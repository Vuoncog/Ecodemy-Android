package com.kltn.ecodemy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary2

@Composable
fun CourseTypeTag(
    text: String
) {
    Box(
        modifier = Modifier
            .clip(Constant.BORDER_SHAPE)
            .background(color = Primary2)
            .padding(
                horizontal = COURSE_TAG_PADDING_HORIZONTAL,
                vertical = COURSE_TAG_PADDING_VERTICAL
            )
    ) {
        Text(
            text = text,
            style = Nunito.Subtitle2.textStyle,
            color = Neutral1,)
    }
}

@Preview
@Composable
fun CourseTypeTagPrev() {
    CourseTypeTag(text = "Android")
}