package com.kltn.ecodemy.ui.screens.course.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.components.SelectionButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito

private val TAB_HORIZONTAL_PADDING = Arrangement.spacedBy(16.dp)
private val HORIZONTAL_PADDING = 16.dp
private val VERTICAL_PADDING = 16.dp

//private const val OVERVIEW = "Overview"
//private const val LECTURES = "Lectures"
//private const val REVIEW = "Review"
//
//private val selectionTab = mapOf(
//    1 to OVERVIEW,
//    2 to LECTURES,
//    3 to REVIEW
//)

@Composable
fun CourseSelectionTab(
    onCheck: MutableState<Int>,
) {
    val OVERVIEW = stringResource(id = R.string.course_overview)
    val LECTURES = stringResource(id = R.string.course_lectures)
    val REVIEW = stringResource(id = R.string.course_review)

    val selectionTab = mapOf(
        1 to OVERVIEW,
        2 to LECTURES,
        3 to REVIEW
    )
    Row(
        horizontalArrangement = TAB_HORIZONTAL_PADDING,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = HORIZONTAL_PADDING,
                vertical = VERTICAL_PADDING
            )
            .horizontalScroll(rememberScrollState())
    ) {
        SelectionButton(
            textContent = selectionTab[1] ?: OVERVIEW,
            selected = selectionTab[onCheck.value] ?: OVERVIEW,
            onUnselectedButtonClicked = { onCheck.value = 1 }
        )
        SelectionButton(
            textContent = selectionTab[2] ?: LECTURES,
            selected = selectionTab[onCheck.value] ?: LECTURES,
            onUnselectedButtonClicked = { onCheck.value = 2 }
        )
        SelectionButton(
            textContent = selectionTab[3] ?: REVIEW,
            selected = selectionTab[onCheck.value] ?: REVIEW,
            onUnselectedButtonClicked = { onCheck.value = 3 }
        )
    }
}

@Composable
fun CourseTabAbout(
    aboutContent: String,
    lectureContent: String,
    prerequisiteContent: String,
    outputContent: String,
    paddingValues: PaddingValues,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                horizontal = HORIZONTAL_PADDING,
            )
            .padding(bottom = 8.dp)
            .padding(paddingValues)
    ) {
        Text(
            text = stringResource(R.string.about),
            style = Nunito.Title1.textStyle,
            color = Neutral1,
        )
        Text(
            text = aboutContent,
            style = Nunito.Body.textStyle,
            color = Neutral1,
        )

        Text(
            text = stringResource(R.string.lecture),
            style = Nunito.Title1.textStyle,
            color = Neutral1,
        )
        Text(
            text = lectureContent,
            style = Nunito.Body.textStyle,
            color = Neutral1,
        )

        Text(
            text = stringResource(R.string.prerequisite),
            style = Nunito.Title1.textStyle,
            color = Neutral1,
        )
        Text(
            text = prerequisiteContent,
            style = Nunito.Body.textStyle,
            color = Neutral1,
        )

        Text(
            text = stringResource(R.string.output),
            style = Nunito.Title1.textStyle,
            color = Neutral1,
        )
        Text(
            text = outputContent,
            style = Nunito.Body.textStyle,
            color = Neutral1,
        )
    }
}