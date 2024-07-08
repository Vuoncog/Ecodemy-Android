package com.kltn.ecodemy.ui.screens.course.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.CONTACTDATE
import com.kltn.ecodemy.constant.Constant.EMAIL
import com.kltn.ecodemy.constant.Constant.FULLNAME
import com.kltn.ecodemy.constant.Constant.PHONE
import com.kltn.ecodemy.constant.formatToString
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.EcodemyTextField
import com.kltn.ecodemy.ui.components.EcodemyTextFieldFullCase
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterForm(
    fullName: String,
    onFullNameChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    phone: String,
    onPhoneChanged: (String) -> Unit,
    contactDate: String,
    onContactDateChanged: (String) -> Unit,
    fullNameKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    phoneKeyboard: Pair<KeyboardActions, KeyboardOptions>,
    isFilled: Boolean = false,
    onRegisterClicked: () -> Unit,
    errorMessage: Map<String, String>,
    isFullNameWrong: Boolean,
    isEmailWrong: Boolean,
    isPhoneWrong: Boolean,
) {
    val openDialog = remember {
        mutableStateOf(false)
    }
    val openTimeDialog = remember {
        mutableStateOf(false)
    }

    val localDateTime = remember {
        mutableStateOf(LocalDateTime.now())
    }

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(id = R.string.keyword),
            color = Neutral1
        )

        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.user,
            label = FULLNAME,
            value = fullName,
            onValueChanged = onFullNameChanged,
            keyboardOptions = fullNameKeyboard.second,
            keyboardActions = fullNameKeyboard.first,
            errorMessage = errorMessage[FULLNAME] ?: "",
            isWrong = isFullNameWrong
        )

        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.envelope,
            label = EMAIL,
            value = email,
            onValueChanged = onEmailChanged,
            keyboardOptions = fullNameKeyboard.second,
            keyboardActions = fullNameKeyboard.first,
            errorMessage = errorMessage[EMAIL] ?: "",
            isWrong = isEmailWrong
        )

        EcodemyTextFieldFullCase(
            leadingIcon = R.drawable.phone,
            label = PHONE,
            value = phone,
            onValueChanged = onPhoneChanged,
            keyboardOptions = phoneKeyboard.second.copy(
                keyboardType = KeyboardType.NumberPassword
            ),
            keyboardActions = phoneKeyboard.first,
            errorMessage = errorMessage[PHONE] ?: "",
            isWrong = isPhoneWrong
        )

        EcodemyTextField(
            leadingIcon = R.drawable.calendar,
            label = CONTACTDATE,
            value = contactDate,
            enabled = false,
            onValueChanged = {
                onContactDateChanged(it)
            },
            trailingIcon = R.drawable.calendar_edit,
            onTrailingIconClicked = {
                openDialog.value = true
            },
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                openDialog.value = true
            }
        )

        EcodemyText(
            format = Nunito.Subtitle1,
            data = stringResource(R.string.please_sure_that_your_information_is_right),
            color = Neutral2
        )

        KocoButton(
            textContent = stringResource(R.string.register), icon = null,
            disable = !isFilled,
            onClick = onRegisterClicked,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
    }
    if (openDialog.value) {
        EcodemyDatePicker(
            openDialog = openDialog,
            openTimeDialog = openTimeDialog,
            onDateClicked = {
                localDateTime.value = it
            }
        )
    }
    if (openTimeDialog.value) {
        EcodemyTimePicker(
            openTimeDialog = openTimeDialog,
            onTimeClicked = { hour, minute ->
                localDateTime.value = localDateTime.value.toLocalDate().atTime(hour, minute)
                onContactDateChanged(localDateTime.value.formatToString())
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcodemyDatePicker(
    openDialog: MutableState<Boolean>,
    openTimeDialog: MutableState<Boolean>,
    onDateClicked: (LocalDateTime) -> Unit,
) {
    val datePicker = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date =
                    Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.systemDefault()).toLocalDate()
                val today = LocalDate.now()
                val expiredDayOfCourse = LocalDate.now().plusDays(7)
                return date in today..expiredDayOfCourse

            }

            override fun isSelectableYear(year: Int): Boolean {
                return year <= LocalDate.now().year
            }
        },
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(

        ),
        modifier = Modifier.padding(horizontal = 16.dp),
        onDismissRequest = { openDialog.value = !openDialog.value },
        confirmButton = {
            TextButton(onClick = {
                openDialog.value = !openDialog.value
                if (datePicker.selectedDateMillis != null) {
                    onDateClicked(
                        LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(datePicker.selectedDateMillis!!),
                            TimeZone.getDefault().toZoneId()
                        )
                    )
                    openTimeDialog.value = true
                }
            }) {
                Text(text = "Choose")
            }
        },
        content = {
            DatePicker(
                state = datePicker,
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcodemyTimePicker(
    openTimeDialog: MutableState<Boolean>,
    onTimeClicked: (Int, Int) -> Unit,
) {
    val timePicker = rememberTimePickerState(
        is24Hour = false
    )

    Dialog(
        onDismissRequest = { openTimeDialog.value = !openTimeDialog.value },
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(
                    Color.White
                )
                .padding(16.dp)
        ) {
            TimePicker(
                state = timePicker,
                layoutType = TimePickerLayoutType.Vertical
            )
            Button(onClick = {
                onTimeClicked(timePicker.hour, timePicker.minute)
                openTimeDialog.value = !openTimeDialog.value
            }) {
                Text(text = "Choose")
            }
        }
    }
}