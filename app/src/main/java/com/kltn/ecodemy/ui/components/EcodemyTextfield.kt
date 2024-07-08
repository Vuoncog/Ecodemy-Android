package com.kltn.ecodemy.ui.components

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.CONTACTDATE
import com.kltn.ecodemy.constant.Constant.EMAIL
import com.kltn.ecodemy.constant.Constant.FULLNAME
import com.kltn.ecodemy.constant.Constant.PASSWORD
import com.kltn.ecodemy.constant.Constant.PHONE
import com.kltn.ecodemy.constant.Constant.REENTERPASSWORD
import com.kltn.ecodemy.constant.formatToString
import com.kltn.ecodemy.constant.textFieldBorder
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import java.time.LocalDate

private val SPACE_BETWEEN = Arrangement.spacedBy(4.dp)
private val PADDING = PaddingValues(
    vertical = 8.dp,
    horizontal = 12.dp,
)

@RequiresApi(Build.VERSION_CODES.O)
private val placeholderMap = mapOf(
    FULLNAME to "Ecodemy",
    EMAIL to "ecodemy@gmail.com",
    PASSWORD to "******",
    REENTERPASSWORD to "******",
    PHONE to "0123456789",
    CONTACTDATE to LocalDate.now().atTime(0, 0).formatToString()
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EcodemyTextField(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int,
    label: String,
    value: String,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit,
    onTrailingIconClicked: () -> Unit = {},
    @DrawableRes trailingIcon: Int? = null,
    isWrong: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val visualTransformation: VisualTransformation by lazy {
        when (label) {
            PASSWORD, REENTERPASSWORD -> {
                if (trailingIcon == R.drawable.hide) PasswordVisualTransformation()
                else VisualTransformation.None
            }

            else -> VisualTransformation.None
        }
    }

    val color by lazy {
        if (isWrong) Danger else Neutral3
    }
    Row(
        horizontalArrangement = SPACE_BETWEEN,
        modifier = Modifier
            .textFieldBorder(color = color)
            .padding(PADDING)
            .height(IntrinsicSize.Min)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(leadingIcon),
            contentDescription = "Leading icon",
            modifier = Modifier.size(32.dp),
            tint = Neutral3
        )
        Divider(
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    vertical = 2.dp,
                    horizontal = 8.dp
                )
                .width(1.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f)
        ) {
            EcodemyText(
                format = Nunito.Subtitle3,
                data = label,
                color = color
            )
            BasicTextField(
                modifier = Modifier,
                enabled = enabled,
                value = value,
                visualTransformation = visualTransformation,
                onValueChange = onValueChanged,
                maxLines = 1,
                textStyle = Nunito.Title1.textStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholderMap[label] ?: "Ecodemy",
                                style = Nunito.Title1.textStyle,
                                color = Neutral2
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        if (trailingIcon != null) {
            Icon(
                imageVector = ImageVector.vectorResource(trailingIcon),
                contentDescription = "Trailing icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onTrailingIconClicked()
                    },
                tint = Neutral3
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EcodemyTextFieldFullCase(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onTrailingIconClicked: () -> Unit = {},
    @DrawableRes trailingIcon: Int? = null,
    isWrong: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        EcodemyTextField(
            modifier = modifier,
            leadingIcon = leadingIcon,
            label = label,
            value = value,
            onValueChanged = onValueChanged,
            onTrailingIconClicked = onTrailingIconClicked,
            trailingIcon = trailingIcon,
            isWrong = isWrong,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
        )

        if (isWrong) {
            EcodemyText(format = Nunito.Subtitle1, data = errorMessage, color = Danger)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AuthTextfieldPrev() {
    EcodemyTextField(
        leadingIcon = R.drawable.user,
        trailingIcon = R.drawable.show_alt,
        label = "Password",
        value = "",
        onValueChanged = {}
    )
}