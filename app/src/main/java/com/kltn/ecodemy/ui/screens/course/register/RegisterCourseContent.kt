package com.kltn.ecodemy.ui.screens.course.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.moveDown
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoScreen
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito

private val REGISTER_COURSE_HEADER_BACKGROUND_HEIGHT = 56.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterCourseContent(
    paddingValues: PaddingValues,
    course: Course,
    fullName: String,
    onFullNameChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    phone: String,
    onPhoneChanged: (String) -> Unit,
    contactDate: String,
    onContactDateChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    isFilled: Boolean = false,
    onRegisterClicked: () -> Unit,
    errorMessage: Map<String, String>,
    isFullNameWrong: Boolean,
    isEmailWrong: Boolean,
    isPhoneWrong: Boolean,
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val state = rememberScrollState()

    KocoScreen(
        headerBackgroundHeight = REGISTER_COURSE_HEADER_BACKGROUND_HEIGHT,
        headerBackgroundShape = RoundedCornerShape(0),
        enableScrollable = true
    ) {
        //LazyColumn
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            //Sticky Header
            Row(
                modifier = Modifier
                    .height(REGISTER_COURSE_HEADER_BACKGROUND_HEIGHT)
                    .padding(horizontal = Constant.PADDING_SCREEN),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = "Back"
                    )
                }
                EcodemyText(
                    format = Nunito.Heading2,
                    data = stringResource(id = R.string.register_course),
                    color = Neutral1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(Constant.ICON_INTERACTIVE_SIZE))
            }

            RegisterCourseInfo(course = course)

            RegisterForm(
                fullName = fullName,
                onFullNameChanged = onFullNameChanged,
                email = email,
                onEmailChanged = onEmailChanged,
                phone = phone,
                onPhoneChanged = onPhoneChanged,
                contactDate = contactDate,
                onContactDateChanged = onContactDateChanged,
                fullNameKeyboard = moveDown(state, scope, focusManager),
                isFilled = isFilled,
                onRegisterClicked = onRegisterClicked,
                errorMessage = errorMessage,
                isFullNameWrong = isFullNameWrong,
                isEmailWrong = isEmailWrong,
                isPhoneWrong = isPhoneWrong,
                phoneKeyboard = Pair(
                    KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    KeyboardOptions(imeAction = ImeAction.Done)
                ),
            )
        }
    }
}