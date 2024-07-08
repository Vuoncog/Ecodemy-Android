package com.kltn.ecodemy.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary2
import com.kltn.ecodemy.ui.theme.Primary3

private val CATEGORY_ITEM_WIDTH = 96.dp
private val CATEGORY_ITEM_PADDING = 20.dp
private val CATEGORY_ITEM_ICON_SIZE = 32.dp

@Composable
fun CategoryItem(
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .width(CATEGORY_ITEM_WIDTH)
            .clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = "Category Item",
            tint = Primary3,
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Primary2)
                .padding(CATEGORY_ITEM_PADDING)
                .size(CATEGORY_ITEM_ICON_SIZE)
        )

        Text(
            text = text,
            style = Nunito.Title2.textStyle,
            color = Neutral2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPrev() {
    CategoryItem(
        icon = R.drawable.mobile_alt,
        onClick = {},
        text = "Mobile"
    )
}