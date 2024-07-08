package com.kltn.ecodemy.ui.screens.course.register

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.RegisterCourseViewModel
import com.kltn.ecodemy.ui.screens.state.EmptyScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterCourseScreen(
    onBackClicked: () -> Unit,
    registerCourseViewModel: RegisterCourseViewModel = hiltViewModel(),
    onSuccessfullyRegister: () -> Unit,
) {
    val context = LocalContext.current
    val registerCourseUiState = registerCourseViewModel.registerCourseUiState.collectAsState().value
    when (registerCourseUiState.course) {
        is RequestState.Success -> {
            Scaffold {
                RegisterCourseContent(
                    paddingValues = it,
                    onBackClicked = onBackClicked,
                    course = registerCourseUiState.course.data,
                    fullName = registerCourseUiState.fullName,
                    onFullNameChanged = registerCourseViewModel::updateFullName,
                    phone = registerCourseUiState.phone,
                    onPhoneChanged = registerCourseViewModel::updatePhone,
                    contactDate = registerCourseUiState.contactDate,
                    onContactDateChanged = registerCourseViewModel::updateContactDate,
                    email = registerCourseUiState.email,
                    onEmailChanged = registerCourseViewModel::updateEmail,
                    isFilled = registerCourseUiState.isFilled,
                    errorMessage = registerCourseUiState.errorMessage,
                    isEmailWrong = !registerCourseUiState.isEmailValid,
                    isFullNameWrong = !registerCourseUiState.isFullNameValid,
                    isPhoneWrong = !registerCourseUiState.isPhoneValid,
                    onRegisterClicked = {
                        registerCourseViewModel.registerClicked(
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.your_registration_form_is_sent),
                                    Toast.LENGTH_SHORT
                                ).show()
                                onSuccessfullyRegister()
                            }
                        )
                    }
                )
            }
        }

        is RequestState.Idle -> {
            EmptyScreen()
        }

        is RequestState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {}
    }
}