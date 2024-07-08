package com.kltn.ecodemy.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.KocoOutlinedButton
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito

private val CATEGORY_HORIZONTAL_PADDING = Arrangement.spacedBy(16.dp)
private val CATEGORY_VERTICAL_PADDING = Arrangement.spacedBy(12.dp)
private val categoriesDefault = listOf("Kotlin", "Coding", "Web")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchCategory(
    modifier: Modifier = Modifier,
    onCategoryItemClick: (String) -> Unit,
    categories: List<String>
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(
                horizontal = Constant.PADDING_SCREEN,
            )
            .padding(Constant.CARD_VERTICAL_PADDING)
            .then(modifier),
        verticalArrangement = Constant.ITEM_SPACE
    ) {
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.category_title),
            color = Neutral1
        )

        FlowRow(
            horizontalArrangement = CATEGORY_HORIZONTAL_PADDING,
            verticalArrangement = CATEGORY_VERTICAL_PADDING,
        ) {
            val categoriesList = categories.ifEmpty { categoriesDefault }
            categoriesList.forEach {
                KocoOutlinedButton(
                    textContent = it,
                    icon = null,
                    onClick = { onCategoryItemClick(it) }
                )
            }
        }
    }
}