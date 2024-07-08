package com.kltn.ecodemy.ui.screens.course.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.constant.moveDown
import com.kltn.ecodemy.constant.textFieldBorder
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Warning

private val placeholderMap = mapOf(
    R.string.title to R.string.summary,
    R.string.content to R.string.write_your_review
)

@Composable
fun ReviewContent(
    paddingValues: PaddingValues,
    title: TextFieldValue,
    titleError: Boolean,
    onTitleChanged: (TextFieldValue) -> Unit,
    content: TextFieldValue,
    contentError: Boolean,
    onContentChanged: (TextFieldValue) -> Unit,
    rate: MutableState<Int>,
) {
    val state = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .verticalScroll(state = state)
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ReviewRatingStar(filledStar = rate.value) {
            rate.value = it + 1
        }

        ReviewTextField(
            label = R.string.title,
            isWrong = titleError,
            value = title,
            onValueChanged = onTitleChanged,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    moveDown(
                        state = state,
                        scope = scope,
                        focusManager = focusManager
                    )
                }
            )
        )

        ReviewTextField(
            label = R.string.content,
            value = content,
            isWrong = contentError,
            onValueChanged = onContentChanged,
            maxLines = Int.MAX_VALUE,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}

@Composable
fun ReviewRatingStar(
    filledStar: Int,
    onStarClicked: (Int) -> Unit,
) {
    val chosenStar = ImageVector.vectorResource(id = R.drawable.star)
    val outlineStar = ImageVector.vectorResource(id = R.drawable.star_outlined)
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        repeat(5) {
            Icon(
                imageVector = if (it > filledStar - 1) outlineStar else chosenStar,
                contentDescription = "Star",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f / 1f)
                    .clickableWithoutRippleEffect(true) {
                        onStarClicked(it)
                    },
                tint = if (it > filledStar - 1) Neutral1 else Warning
            )
        }
    }
}

@Composable
fun ReviewTextField(
    label: Int,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    maxLines: Int = 1,
    isWrong: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val color by lazy {
        if (isWrong) Danger else Neutral3
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        EcodemyText(
            format = Nunito.Title1,
            data = stringResource(id = label),
            color = if (value.text.isEmpty()) Neutral1 else Neutral3
        )
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .textFieldBorder(color = color)
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
            value = value,
            onValueChange = onValueChanged,
            textStyle = Nunito.Title1.textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.padding(4.dp)
                ) {
                    if (value.text.isEmpty()) {
                        Text(
                            text =
                            stringResource(
                                id = placeholderMap[label] ?: R.string.title
                            ),
                            style = Nunito.Subtitle1.textStyle,
                            color = Neutral3
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}