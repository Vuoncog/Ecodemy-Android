package com.kltn.ecodemy.ui.screens.authenication.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.Primary2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    textContent: String,
    @DrawableRes icon: Int?,
    disable: Boolean = false,
    onClick: () -> Unit,
    borderColor: Color = Color.White
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")

    val containerColor = if (isPressed) Color.White.copy(0.7f)
    else Color.White.copy(alpha = 0.5f)
    val paddingValues: PaddingValues = if (icon == null) {
        PaddingValues(
            vertical = 4.dp,
            horizontal = 12.dp
        )
    } else {
        PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = 16.dp,
            end = 20.dp
        )
    }
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            onClick = onClick,
            contentPadding = paddingValues,
            shape = Constant.BORDER_SHAPE,
            modifier = Modifier
                .then(modifier)
                .scale(scale)
                .defaultMinSize(minHeight = 1.dp)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                disabledContainerColor = Primary2,
            ),
            enabled = !disable,
            interactionSource = interactionSource
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (icon != null) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = icon),
                        contentDescription = "Icon Button",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = textContent,
                    style = if (icon != null) Nunito.Title1.textStyle else
                        Nunito.Subtitle1.textStyle,
                    textAlign = TextAlign.Center,
                    color = Neutral1
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthBackButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.arrow_back,
    disable: Boolean = false,
    onClick: () -> Unit,
    borderColor: Color = Color.White
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")

    val containerColor = if (isPressed) Color.White.copy(0.7f)
    else Color.White.copy(alpha = 0.5f)
    val paddingValues =
        PaddingValues(
            vertical = 4.dp,
            horizontal = 12.dp
        )

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            contentPadding = paddingValues,
            modifier = Modifier
                .then(modifier)
                .scale(scale)
                .defaultMinSize(minHeight = 1.dp, minWidth = 1.dp)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                disabledContainerColor = Primary2,
            ),
            enabled = !disable,
            interactionSource = interactionSource
        ) {

            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = "Icon Button",
                modifier = Modifier
                    .size(20.dp),
                tint = Neutral2
            )
        }
    }
}

@Preview
@Composable
fun AuthButtonPrev() {
    AuthButton(
        textContent = "Button",
        icon = R.drawable.google,
        onClick = {},
        borderColor = Primary1
    )
}

@Preview
@Composable
fun AuthButtonPrev2() {
    AuthButton(
        textContent = "Button",
        icon = null,
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun AuthBackButtonPrev() {
    AuthBackButton(
        onClick = {},
    )
}