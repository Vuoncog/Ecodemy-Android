package com.kltn.ecodemy.domain.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.EMAIL
import com.kltn.ecodemy.constant.Constant.PASSWORD
import com.kltn.ecodemy.constant.Constant.REENTERPASSWORD
import com.kltn.ecodemy.constant.Constant.USER
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.TextFieldValidationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val textFieldValidationRepository: TextFieldValidationRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _signupUiState = MutableStateFlow(SignupUiState())
    val signupUiState = _signupUiState.asStateFlow()
    val otp = mutableStateOf("")
    private val minuteExpired = 2
    private val currentTime = mutableLongStateOf(
        System.currentTimeMillis()
    )

    fun updateFullName(fullName: String) {
        _signupUiState.value = _signupUiState.value.copy(
            fullName = fullName
        )
    }

    fun updatePassword(password: String) {
        _signupUiState.value = _signupUiState.value.copy(
            password = password
        )
    }

    fun updateReEnterPassword(reEnterPassword: String) {
        _signupUiState.value = _signupUiState.value.copy(
            reEnterPassword = reEnterPassword
        )
    }

    fun updateEmail(email: String) {
        _signupUiState.value = _signupUiState.value.copy(
            email = email
        )
    }

    fun updateHidePassword() {
        val hidePassword = _signupUiState.value.hidePassword
        _signupUiState.value = _signupUiState.value.copy(
            hidePassword = !hidePassword
        )
    }

    fun updateHideReEnterPassword() {
        val hideReEnterPassword = _signupUiState.value.hideReEnterPassword
        _signupUiState.value = _signupUiState.value.copy(
            hideReEnterPassword = !hideReEnterPassword
        )
    }

    fun resendOTP() {
        viewModelScope.launch {
            val email = _signupUiState.value.email
            val otp = (1000..9999).random()
            try {
                this@SignupViewModel.otp.value = if (otp < 1000) "0$otp" else otp.toString()
                Log.d("OTP Random", this@SignupViewModel.otp.value)
                _signupUiState.value = _signupUiState.value.copy(
                    isLoading = true,
                    resend = false
                )
                authenticationRepository.otpRequest(email, this@SignupViewModel.otp.value)
                _signupUiState.value = _signupUiState.value.copy(
                    isLoading = false,
                    otpError = ""
                )
                currentTime.longValue = System.currentTimeMillis()
            } catch (e: HttpException) {
                Toast.makeText(context, e.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun receiveOTP(
        onSucceed: () -> Unit,
    ) {
        val checkValid = checkValid(
            email = _signupUiState.value.email,
            fullName = _signupUiState.value.fullName,
            password = _signupUiState.value.password,
            reEnterPassword = _signupUiState.value.reEnterPassword,
        )
        addErrorMessage()
        if (checkValid) {
            viewModelScope.launch {
                val email = _signupUiState.value.email
                val otp = (1000..9999).random()
                try {
                    this@SignupViewModel.otp.value = if (otp < 1000) "0$otp" else otp.toString()
                    Log.d("OTP Random", this@SignupViewModel.otp.value)
                    _signupUiState.value = _signupUiState.value.copy(
                        isLoading = true,
                        resend = false
                    )
                    authenticationRepository.otpRequest(email, this@SignupViewModel.otp.value)
                    _signupUiState.value = _signupUiState.value.copy(
                        isLoading = false
                    )
                    currentTime.longValue = System.currentTimeMillis()
                    onSucceed()
                } catch (e: HttpException) {
                    Toast.makeText(context, e.message(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun signup(
        onSucceed: () -> Unit,
        inputOTP: String,
    ) {
        _signupUiState.value = _signupUiState.value.copy(
            otpError = "",
            isLoading = true
        )
        val checkOTP = inputOTP == this.otp.value
        val available =
            (System.currentTimeMillis() - currentTime.longValue) in 0..60000 * minuteExpired
        if (!available) {
            _signupUiState.value = _signupUiState.value.copy(
                otpError = context.getString(R.string.otp_is_expired),
                isLoading = false,
                resend = true,
            )
            return
        }

        if (checkOTP) {
            viewModelScope.launch {
                try {
                    val email = _signupUiState.value.email
                    val password = _signupUiState.value.password
                    val fullName = _signupUiState.value.fullName

                    val added = authenticationRepository.addNewUser(
                        email, password, fullName
                    )
                    if (added) {
                        onSucceed()
                        _signupUiState.value = _signupUiState.value.copy(
                            isLoading = false,
                        )
                    }
                } catch (e: Exception) {
                    _signupUiState.value = _signupUiState.value.copy(
                        otpError = context.getString(R.string.account_is_created_before),
                        isLoading = false,
                    )
                }
            }
        } else {
            _signupUiState.value = _signupUiState.value.copy(
                otpError = context.getString(R.string.otp_is_invalid_please_try_again),
                isLoading = false,
            )
            Log.d("Valid to sign up", "No")
        }
    }

    private fun checkMail(email: String): Boolean = textFieldValidationRepository.checkMail(email)
    private fun checkFullName(fullName: String) =
        textFieldValidationRepository.checkFullName(fullName)

    private fun checkPassword(password: String) =
        textFieldValidationRepository.checkPassword(password)

    private fun checkReEnterPassword(password: String, reEnterPassword: String) =
        textFieldValidationRepository.checkReEnterPassword(password, reEnterPassword)

    private fun checkValid(
        email: String,
        fullName: String,
        password: String,
        reEnterPassword: String
    ): Boolean {
        val isFullNameValid = checkFullName(fullName)
        val isEmailValid = checkMail(email)
        val isPasswordValid = checkPassword(password)
        val isReEnterPasswordValid =
            if (password.isNotEmpty()) checkReEnterPassword(password, reEnterPassword) else true
        _signupUiState.value = _signupUiState.value.copy(
            isFullNameValid = isFullNameValid,
            isEmailValid = isEmailValid,
            isPasswordValid = isPasswordValid,
            isReEnterPasswordValid = isReEnterPasswordValid,
        )
        return isFullNameValid.and(isEmailValid)
            .and(isPasswordValid).and(isReEnterPasswordValid)
    }

    private fun addErrorMessage() {
        val errorMessage = mutableMapOf<String, String>()
        if (_signupUiState.value.fullName.isEmpty()) {
            errorMessage[USER] =
                context.getString(R.string.please_fill_in_full_name)
        } else if (!_signupUiState.value.isFullNameValid) {
            errorMessage[USER] = context.getString(R.string.full_name_does_not_have_symbols)
        }

        if (_signupUiState.value.email.isEmpty()) {
            errorMessage[EMAIL] = context.getString(R.string.please_fill_in_email)
        } else if (!_signupUiState.value.isEmailValid) {
            errorMessage[EMAIL] = context.getString(R.string.email_is_not_valid)
        }


        if (_signupUiState.value.password.isEmpty()) {
            errorMessage[PASSWORD] =
                context.getString(R.string.please_fill_in_password)
        } else if (!_signupUiState.value.isPasswordValid) {
            errorMessage[PASSWORD] =
                context.getString(R.string.password_required)
        }

        if (_signupUiState.value.reEnterPassword.isEmpty()) {
            errorMessage[REENTERPASSWORD] =
                context.getString(R.string.please_fill_in_re_enter_password)
        } else if (!_signupUiState.value.isReEnterPasswordValid) {
            errorMessage[REENTERPASSWORD] = context.getString(R.string.password_don_t_match)
        }

        _signupUiState.value = _signupUiState.value.copy(
            errorMessage = errorMessage
        )
    }
}

data class SignupUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val reEnterPassword: String = "",
    val hidePassword: Boolean = true,
    val hideReEnterPassword: Boolean = true,
    val errorMessage: Map<String, String> = mapOf(),
    val isFullNameValid: Boolean = true,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isReEnterPasswordValid: Boolean = true,
    val isLoading: Boolean = false,
    val otpError: String = "",
    val resend: Boolean = false,
)