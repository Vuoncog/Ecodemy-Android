package com.kltn.ecodemy.ui.screens.wishlist

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary2
import com.kltn.ecodemy.ui.theme.PrimaryPressed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistRemoveButton(
    modifier: Modifier = Modifier,
    textContent: String,
    disable: Boolean = false,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val contentColor = if (isPressed) PrimaryPressed else Danger
    val paddingValues = PaddingValues(
        horizontal = 12.dp,
        vertical = 8.dp
    )
    val blankString = ""
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        TextButton(
            onClick = onClick,
            contentPadding = paddingValues,
            shape = Constant.BORDER_SHAPE,
            modifier = Modifier
                .defaultMinSize(
                    minHeight = 1.dp
                )
                .then(modifier),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Primary2
            ),
            enabled = !disable,
            interactionSource = interactionSource
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = textContent,
                    style = Nunito.Subtitle2.textStyle,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.x),
                    contentDescription = "Icon Button",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BtnPrev() {
    WishlistRemoveButton(textContent = "Remove") {

    }
}