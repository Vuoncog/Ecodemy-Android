package com.kltn.ecodemy.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.BORDER_SHAPE
import com.kltn.ecodemy.constant.Constant.MINIMUM_INTERACTIVE_SIZE
import com.kltn.ecodemy.ui.components.SearchBar
import com.kltn.ecodemy.ui.theme.ContainerColor
import com.kltn.ecodemy.ui.theme.Neutral2

@Composable
fun HomeSearchBar(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().then(modifier)
    ) {
//        SearchBar(
//            value = value,
//            onValueChanged = onValueChanged,
//            modifier = Modifier.weight(1f)
//        )

        IconButton(
            modifier = Modifier
                .clip(BORDER_SHAPE)
                .size(MINIMUM_INTERACTIVE_SIZE)
                .background(ContainerColor),
            onClick = { /*TODO*/ }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.filter),
                contentDescription = "Slider",
                tint = Neutral2
            )
        }
    }
}