package com.kltn.ecodemy.ui.screens.account

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.DangerPressed
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountButton(
    modifier: Modifier = Modifier,
    textContent: String,
    disable: Boolean = false,
    onClick: () -> Unit,
    colorPressed: Color = DangerPressed,
    colorDisplayed: Color = Danger,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val contentColor = if (isPressed) colorPressed else colorDisplayed
    val paddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 10.dp
    )
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        TextButton(
            onClick = onClick,
            contentPadding = paddingValues,
            shape = RoundedCornerShape(0),
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
                EcodemyText(
                    format = Nunito.Title1,
                    data = textContent,
                    color = contentColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}