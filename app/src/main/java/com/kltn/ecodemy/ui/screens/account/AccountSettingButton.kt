package com.kltn.ecodemy.ui.screens.account

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingButton(
    modifier: Modifier = Modifier,
    textContent: String,
    @DrawableRes subIcon: Int?,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val contentColor = if (isPressed) Neutral2 else Neutral1
    val paddingValues: PaddingValues = if (subIcon == null) {
        PaddingValues(
            vertical = 8.dp,
            horizontal = 0.dp
        )
    } else {
        PaddingValues(
            vertical = 8.dp,
            horizontal = 0.dp
        )
    }
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        TextButton(
            onClick = onClick,
            contentPadding = paddingValues,
            shape = RoundedCornerShape(5),
            modifier = Modifier
                .then(modifier),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = contentColor,
            ),
            interactionSource = interactionSource
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    EcodemyText(
                        format = Nunito.Title2,
                        data = textContent,
                        color = Neutral1
                    )
                }
                if (subIcon != null) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = subIcon),
                        contentDescription = "Icon Button",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.chevron_right),
                    contentDescription = "Chevron Right",
                    modifier = Modifier.size(24.dp),
                    tint = Neutral2
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SettingButtonPrev() {
    val localDensity = LocalDensity.current

    // Create element height in pixel state
    var columnHeightPx by remember {
        mutableStateOf(0f)
    }

    // Create element height in dp state
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }

    SettingButton(
        textContent = "$columnHeightDp",
        subIcon = null,
        onClick = {},
        modifier = Modifier.onGloballyPositioned { coordinates ->
            // Set column height using the LayoutCoordinates
            columnHeightPx = coordinates.size.height.toFloat()
            columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
        }
    )
}