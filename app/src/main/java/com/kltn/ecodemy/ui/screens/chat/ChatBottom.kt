package com.kltn.ecodemy.ui.screens.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.topBorder
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

@Composable
fun ChatBottom(
    onSendClicked: () -> Unit,
    value: String,
    onValueChanged: (String) -> Unit,
    onTextFieldClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .topBorder(
                strokeWidth = 1.dp,
                color = Neutral3
            )
            .background(Color.White)
            .padding(
                horizontal = Constant.PADDING_SCREEN,
                vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onTextFieldClicked()
                            }
                        }
                    }
                },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(BackgroundColor)
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            textStyle = Nunito.Body.textStyle,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = "Aa",
                            style = Nunito.Body.textStyle,
                            color = Neutral3
                        )
                    }
                    innerTextField()
                }
            }
        )
        IconButton(
            onClick = {
                onSendClicked()
                      },
            modifier = Modifier.size(24.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.send),
                contentDescription = "Send",
                tint = Primary1
            )
        }
    }
}