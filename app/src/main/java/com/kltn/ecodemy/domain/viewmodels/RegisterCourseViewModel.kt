package com.kltn.ecodemy.domain.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.CourseRepository
import com.kltn.ecodemy.data.repository.RegisterCourseRepository
import com.kltn.ecodemy.data.repository.TextFieldValidationRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.models.user.EnrollContact
import com.kltn.ecodemy.domain.models.user.PaymentStatus
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.user.UserContact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterCourseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository,
    private val textFieldValidationRepository: TextFieldValidationRepository,
    private val registerCourseRepository: RegisterCourseRepository,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val courseId = savedStateHandle.get<String>(key = COURSE_ID)

    private val _registerCourseUiState: MutableStateFlow<RegisterCourseUiState> =
        MutableStateFlow(RegisterCourseUiState())
    val registerCourseUiState = _registerCourseUiState.asStateFlow()

    private lateinit var user: User

    init {
        viewModelScope.launch {
            setUser()
            setCourse()
        }
    }

    private suspend fun setUser() {
        authenticationRepository.refreshUser(coerce = true)
        authenticationRepository.getUser {
            user = it
            if (it != User()) {
                updateEmail(email = it.userInfo.email)
                updateFullName(fullName = it.userInfo.fullName)
                updatePhone(phone = it.userInfo.phone)
            }
        }
    }

    private fun setCourse() {
        viewModelScope.launch {
            if (courseId != null) {
                courseRepository.getCourse(courseId = courseId) {
                    _registerCourseUiState.value = _registerCourseUiState.value.copy(
                        course = RequestState.Success(data = it)
                    )
                }
            }
        }
    }

    fun updateFullName(fullName: String) {
        _registerCourseUiState.value = _registerCourseUiState.value.copy(
            fullName = fullName
        )
        isFilled()
    }

    fun updateEmail(email: String) {
        _registerCourseUiState.value = _registerCourseUiState.value.copy(
            email = email
        )
        isFilled()
    }


    fun updatePhone(phone: String) {
        _registerCourseUiState.value = _registerCourseUiState.value.copy(
            phone = phone
        )
        isFilled()
    }

    fun updateContactDate(contactDate: String) {
        _registerCourseUiState.value = _registerCourseUiState.value.copy(
            contactDate = contactDate
        )
        isFilled()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerClicked(
        onSuccess: () -> Unit,
    ) {
        val checkValid = checkValid(
            email = _registerCourseUiState.value.email,
            fullName = _registerCourseUiState.value.fullName,
            phone = _registerCourseUiState.value.phone,
        )

        addErrorMessage()

        if (checkValid) {
            viewModelScope.launch {
                val email = _registerCourseUiState.value.email
                val phone = _registerCourseUiState.value.phone
                val fullName = _registerCourseUiState.value.fullName
                val contactDate = _registerCourseUiState.value.contactDate

                _registerCourseUiState.value = _registerCourseUiState.value.copy(
                    isFilled = false
                )

                registerCourseRepository.registeredCourseUser(
                    userContact = UserContact(
                        studentId = user._id ?: "User didn't login",
                        courseId = courseId!!,
                        date = contactDate,
                        paymentStatus = PaymentStatus.NOT_COMPLETED.status,
                        enrollContact = EnrollContact(
                            fullName = fullName,
                            phoneNumber = phone,
                            email = email
                        )
                    ),
                    onSuccess = onSuccess
                )
            }
        } else {
            Log.d("Valid to register course", "No")
        }
    }

    private fun isFilled() {
        val fullName = _registerCourseUiState.value.fullName
        val phone = _registerCourseUiState.value.phone
        val contactDate = _registerCourseUiState.value.contactDate
        val email = _registerCourseUiState.value.email
        _registerCourseUiState.value = _registerCourseUiState.value.copy(
            isFilled = fullName.isNotEmpty() && phone.isNotEmpty()
                    && contactDate.isNotEmpty() && email.isNotEmpty()
        )
    }

    private fun checkMail(email: String): Boolean = textFieldValidationRepository.checkMail(email)
    private fun checkFullName(fullName: String) =
        textFieldValidationRepository.checkFullName(fullName)

    private fun checkPhone(phone: String) = textFieldValidationRepository.checkPhone(phone)

    private fun addErrorMessage() {
        val errorMessage = mutableMapOf<String, String>()
        if (!_registerCourseUiState.value.isFullNameValid) {
            errorMessage[Constant.FULLNAME] = "Full name does not have symbols"
        }

        if (!_registerCourseUiState.value.isEmailValid) {
            errorMessage[Constant.EMAIL] = "Email is not valid"
        }

        if (!_registerCourseUiState.value.isPhoneValid) {
            errorMessage[Constant.PHONE] = "Phone number has at least 10 digits"
        }

        _registerCourseUiState.value = _registerCourseUiState.value.copy(
            errorMessage = errorMessage
        )
    }

    private fun checkValid(
        email: String,
        fullName: String,
        phone: String,
    ): Boolean {
        val isFullNameValid = checkFullName(fullName)
        val isEmailValid = checkMail(email)
        val isPhoneValid = checkPhone(phone)

        _registerCourseUiState.value = _registerCourseUiState.value.copy(
            isFullNameValid = isFullNameValid,
            isEmailValid = isEmailValid,
            isPhoneValid = isPhoneValid
        )

        return isFullNameValid.and(isEmailValid).and(isPhoneValid)
    }
}

data class RegisterCourseUiState(
    val course: RequestState<Course> = RequestState.Loading,
    val fullName: String = "",
    val phone: String = "",
    val contactDate: String = "",
    val email: String = "",
    val isFilled: Boolean = false,
    val errorMessage: Map<String, String> = mapOf(),
    val isFullNameValid: Boolean = true,
    val isEmailValid: Boolean = true,
    val isPhoneValid: Boolean = true,
)