package com.kltn.ecodemy.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.BORDER_SHAPE
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.Primary2
import com.kltn.ecodemy.ui.theme.Primary3
import com.kltn.ecodemy.ui.theme.PrimaryPressed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KocoButton(
    modifier: Modifier = Modifier,
    textContent: String,
    @DrawableRes icon: Int?,
    disable: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")

    val containerColor = if (isPressed) PrimaryPressed else Primary1
    val paddingValues: PaddingValues = if (icon == null) {
        PaddingValues(
            vertical = 10.dp,
            horizontal = 16.dp
        )
    } else {
        PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = 12.dp,
            end = 12.dp
        )
    }
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            onClick = onClick,
            contentPadding = paddingValues,
            shape = BORDER_SHAPE,
            modifier = Modifier
                .then(modifier),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = Neutral1,
                disabledContainerColor = Primary2,
                disabledContentColor = Neutral2
            ),
            enabled = !disable,
            interactionSource = interactionSource
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .scale(scale)
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = icon),
                        contentDescription = "Icon Button",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = textContent,
                    style = Nunito.Title1.textStyle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KocoTextButton(
    modifier: Modifier = Modifier,
    textContent: String,
    @DrawableRes icon: Int?,
    disable: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val contentColor = if (isPressed) PrimaryPressed else Primary1
    val paddingValues: PaddingValues = if (icon == null) {
        PaddingValues(
            vertical = 10.dp,
            horizontal = 16.dp
        )
    } else {
        PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = 12.dp,
            end = 12.dp
        )
    }
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        TextButton(
            onClick = onClick,
            contentPadding = paddingValues,
            shape = BORDER_SHAPE,
            modifier = Modifier.then(modifier),
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = icon),
                        contentDescription = "Icon Button",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = textContent,
                    style = Nunito.Title1.textStyle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KocoOutlinedButton(
    modifier: Modifier = Modifier,
    textContent: String,
    @DrawableRes icon: Int?,
    disable: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val contentColor = if (isPressed) PrimaryPressed else Primary3
    val paddingValues: PaddingValues = if (icon == null) {
        PaddingValues(
            vertical = 10.dp,
            horizontal = 16.dp
        )
    } else {
        PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = 12.dp,
            end = 12.dp
        )
    }
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        OutlinedButton(
            onClick = onClick,
            contentPadding = paddingValues,
            shape = BORDER_SHAPE,
            modifier = Modifier.then(modifier),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Primary2
            ),
            enabled = !disable,
            interactionSource = interactionSource,
            border = BorderStroke(
                width = 1.dp,
                color = if (!disable) contentColor else Primary2
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = icon),
                        contentDescription = "Icon Button",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = textContent,
                    style = Nunito.Title1.textStyle,
                    textAlign = TextAlign.Center,
                    color = if (!disable) contentColor else Primary2
                )
            }
        }
    }
}

@Preview
@Composable
fun KocoButtonPrev() {
    KocoButton(
        textContent = "Button",
        icon = R.drawable.home_alt_2,
        onClick = {}
    )
}

@Preview
@Composable
fun KocoButtonPrev2() {
    KocoButton(
        textContent = "Button",
        icon = null,
        disable = true,
        onClick = {}
    )
}

@Preview
@Composable
fun KocoButtonPrev3() {
    KocoTextButton(
        textContent = "Button",
        icon = R.drawable.home_alt_2,
        onClick = {}
    )
}

@Preview
@Composable
fun KocoButtonPrev4() {
    KocoTextButton(
        textContent = "Button",
        icon = null,
        disable = true,
        onClick = {}
    )
}

@Preview
@Composable
fun KocoButtonPrev5() {
    KocoOutlinedButton(
        textContent = "Button",
        icon = null,
        disable = true,
        onClick = {}
    )
}

@Preview
@Composable
fun KocoButtonPrev6() {
    KocoOutlinedButton(
        textContent = "Button",
        icon = R.drawable.home_alt_2,
        onClick = {}
    )
}