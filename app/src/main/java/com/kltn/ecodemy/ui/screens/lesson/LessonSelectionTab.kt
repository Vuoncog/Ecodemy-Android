package com.kltn.ecodemy.ui.screens.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.components.KocoTextButton
import com.kltn.ecodemy.ui.components.SelectionButton

private val TAB_HORIZONTAL_PADDING = Arrangement.spacedBy(16.dp)
private val HORIZONTAL_PADDING = 16.dp

private const val LECTURES = "Lectures"
private const val RESOURCES = "Resources"

private val selectionTab = mapOf(
    1 to LECTURES,
    2 to RESOURCES
)

@Composable
fun LessonSelectionTab(
    onCheck: MutableState<Int>,
) {
    Row(
        horizontalArrangement = TAB_HORIZONTAL_PADDING,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = HORIZONTAL_PADDING,
            )
            .padding(
                top = 16.dp,
                bottom = 8.dp
            )
            .horizontalScroll(rememberScrollState())
    ) {
        SelectionButton(
            textContent = selectionTab[1] ?: LECTURES,
            selected = selectionTab[onCheck.value] ?: LECTURES,
            onUnselectedButtonClicked = { onCheck.value = 1 }
        )
        SelectionButton(
            textContent = selectionTab[2] ?: RESOURCES,
            selected = selectionTab[onCheck.value] ?: RESOURCES,
            onUnselectedButtonClicked = { onCheck.value = 2 }
        )
    }
}