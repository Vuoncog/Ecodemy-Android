package com.kltn.ecodemy.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.EndLinear
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary2
import com.kltn.ecodemy.ui.theme.StartLinear

@Composable
fun ChatTopBar(
    user: String = "Chat",
    onListClicked: (() -> Unit)? = null,
    onBackClicked: () -> Unit,
    height: Dp = 56.dp,
) {
    Row(
        modifier = Modifier
            .height(height)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(StartLinear, EndLinear),
                    start = Offset.Infinite,
                    end = Offset.Zero
                )
            )
            .padding(
                end = 4.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                contentDescription = "Back",
                tint = Neutral1
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = user,
                style = Nunito.Heading2.textStyle,
                color = Neutral1,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (onListClicked != null){
            IconButton(onClick = onListClicked) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.list),
                    contentDescription = "List",
                    tint = Neutral1
                )
            }
        }else {
            Spacer(modifier = Modifier.size(Constant.ICON_INTERACTIVE_SIZE))
        }
    }
}