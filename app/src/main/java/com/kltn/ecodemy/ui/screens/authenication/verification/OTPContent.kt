package com.kltn.ecodemy.ui.screens.authenication.verification

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

@Composable
fun OTP(
    first: String,
    second: String,
    third: String,
    fourth: String,
    onFirstChanged: (String) -> Unit,
    onSecondChanged: (String) -> Unit,
    onThirdChanged: (String) -> Unit,
    onFourthChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextFieldSquare(
            value = first,
            onFirstChar = {
                focusManager.moveFocus(FocusDirection.Next)
            },
            onValueChanged = onFirstChanged
        )

        TextFieldSquare(
            value = second,
            onFirstChar = {
                focusManager.moveFocus(FocusDirection.Next)
            },
            onValueChanged = onSecondChanged
        )
        TextFieldSquare(
            value = third,
            onFirstChar = {
                focusManager.moveFocus(FocusDirection.Next)
            },
            onValueChanged = onThirdChanged
        )
        TextFieldSquare(
            value = fourth,
            onFirstChar = {
                focusManager.clearFocus()
            },
            onValueChanged = onFourthChanged
        )
    }
}

@Composable
fun TextFieldSquare(
    modifier: Modifier = Modifier,
    value: String,
    onFirstChar: () -> Unit,
    onValueChanged: (String) -> Unit,
) {
    val color = remember {
        mutableStateOf(Neutral3)
    }
    BasicTextField(
        value = value, onValueChange = {
            if (it.isNotEmpty()) {
                onFirstChar()
                onValueChanged(it.replace(value, ""))
            }
        },
        textStyle = Nunito.Heading1.textStyle.copy(
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.then(modifier),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next
        ),
        visualTransformation = VisualTransformation.None,
        decorationBox = {
            Box(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            color.value = Primary1
                        } else {
                            color.value = Neutral3
                        }
                    }
                    .border(
                        width = 1.dp,
                        color = color.value,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp,
                    )
                    .width(24.dp)
                    .height(33.dp),
                contentAlignment = Alignment.Center
            ) {
                it()
            }
        })
}