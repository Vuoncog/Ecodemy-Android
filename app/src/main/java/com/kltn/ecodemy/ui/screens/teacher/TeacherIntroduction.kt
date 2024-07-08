package com.kltn.ecodemy.ui.screens.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.Constant.PADDING_SCREEN
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito

private val TEACHER_INTRODUCTION_PADDING = PaddingValues(
    start = PADDING_SCREEN,
    end = PADDING_SCREEN,
    top = 12.dp,
    bottom = 8.dp
)

private val TEACHER_INTRODUCTION_SPACE_BETWEEN = Arrangement.spacedBy(8.dp)

@Composable
fun TeacherIntroduction(
    about: String,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(TEACHER_INTRODUCTION_PADDING),
        verticalArrangement = TEACHER_INTRODUCTION_SPACE_BETWEEN
    ) {
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.teacher_introduction),
            color = Neutral1
        )
        EcodemyText(
            format = Nunito.Body,
            data = about,
            color = Neutral1
        )
    }
}