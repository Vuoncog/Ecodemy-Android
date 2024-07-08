package com.kltn.ecodemy.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.course.Category
import com.kltn.ecodemy.ui.components.CategoryItem
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito

private val CATEGORY_ITEM_SPACE_BETWEEN = Arrangement.spacedBy(8.dp)

@Composable
fun HomeCategory(
    modifier: Modifier = Modifier,
    onCategoryItemClick: (String) -> Unit
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
        Row(
            horizontalArrangement = CATEGORY_ITEM_SPACE_BETWEEN,
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            Category.entries.forEach { category ->
                CategoryItem(
                    icon = category.icon,
                    text = category.name,
                    onClick = { onCategoryItemClick(category.name) }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeCategoryPrev() {
    HomeCategory {

    }
}