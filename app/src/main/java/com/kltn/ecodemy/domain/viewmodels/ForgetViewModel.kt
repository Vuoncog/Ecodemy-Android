package com.kltn.ecodemy.domain.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.TextFieldValidationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class ForgetViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val textFieldValidationRepository: TextFieldValidationRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _forgetUiState = MutableStateFlow(ForgetUiState())
    val otp = mutableStateOf("")
    val forgetUiState = _forgetUiState.asStateFlow()
    private val minuteExpired = 2
    private val currentTime = mutableLongStateOf(
        System.currentTimeMillis()
    )

    fun clearWhenBackToLogin(){
        _forgetUiState.value = _forgetUiState.value.copy(
            email = "",
            isLoading = false
        )
    }

    fun clearWhenBackToForget(){
        _forgetUiState.value = _forgetUiState.value.copy(
            password = "",
            reEnterPasswordError = "",
            isLoading = false
        )
    }

    fun onEmailChanged(email: String) {
        _forgetUiState.value = _forgetUiState.value.copy(
            email = email
        )
    }

    fun onPasswordChanged(password: String) {
        _forgetUiState.value = _forgetUiState.value.copy(
            password = password
        )
    }

    fun onReEnterPasswordChanged(reEnterPassword: String) {
        _forgetUiState.value = _forgetUiState.value.copy(
            reEnterPassword = reEnterPassword
        )
    }

    fun applyNewPassword(
        onSucceed: () -> Unit
    ){
        val email = _forgetUiState.value.email
        val password = _forgetUiState.value.password
        val reEnterPassword = _forgetUiState.value.reEnterPassword
        val checkValid = checkPassword(password) && checkReEnterPassword(password, reEnterPassword)
        addErrorPasswordMessage()

        if (checkValid){
            viewModelScope.launch {
                authenticationRepository.applyNewPassword(email, reEnterPassword)
                onSucceed()
            }
        }
    }

    fun verify(
        onSucceed: () -> Unit,
        inputOTP: String,
    ){
        _forgetUiState.value = _forgetUiState.value.copy(
            otpError = "",
            isLoading = true
        )
        val checkOTP = inputOTP == this.otp.value
        val available =
            (System.currentTimeMillis() - currentTime.longValue) in 0..60000 * minuteExpired
        if (!available) {
            _forgetUiState.value = _forgetUiState.value.copy(
                otpError = context.getString(R.string.otp_is_expired),
                isLoading = false,
                resend = true,
            )
            return
        }
        if (checkOTP) {
            _forgetUiState.value = _forgetUiState.value.copy(
                isLoading = false,
            )
            onSucceed()
        } else {
            _forgetUiState.value = _forgetUiState.value.copy(
                otpError = context.getString(R.string.otp_is_invalid_please_try_again),
                isLoading = false,
            )
        }
    }

    fun resendOTP() {
        viewModelScope.launch {
            val email = _forgetUiState.value.email
            val otp = (1000..9999).random()
            try {
                this@ForgetViewModel.otp.value = if (otp < 1000) "0$otp" else otp.toString()
                Log.d("OTP Random", this@ForgetViewModel.otp.value)
                _forgetUiState.value = _forgetUiState.value.copy(
                    isLoading = true,
                    resend = false
                )
                authenticationRepository.otpResetPassRequest(email, this@ForgetViewModel.otp.value)
                _forgetUiState.value = _forgetUiState.value.copy(
                    isLoading = false
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
        val email = _forgetUiState.value.email
        val checkValid = checkMail(email = email)
        addErrorMessage()
        if (checkValid) {
            viewModelScope.launch {
                val otp = (1000..9999).random()
                try {
                    this@ForgetViewModel.otp.value = if (otp < 1000) "0$otp" else otp.toString()
                    _forgetUiState.value = _forgetUiState.value.copy(
                        isLoading = true,
                        resend = false
                    )
                    authenticationRepository.otpResetPassRequest(email, this@ForgetViewModel.otp.value)
                    _forgetUiState.value = _forgetUiState.value.copy(
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
    private fun checkMail(email: String): Boolean = textFieldValidationRepository.checkMail(email)

    private fun checkPassword(password: String) =
        textFieldValidationRepository.checkPassword(password)

    private fun checkReEnterPassword(password: String, reEnterPassword: String) =
        textFieldValidationRepository.checkReEnterPassword(password, reEnterPassword)

    private fun addErrorMessage(){
        val email = _forgetUiState.value.email
        if (email.isEmpty()) {
            _forgetUiState.value = _forgetUiState.value.copy(
                emailError = context.getString(R.string.please_fill_in_email)
            )
        } else if (!checkMail(email)) {
            _forgetUiState.value = _forgetUiState.value.copy(
                emailError = context.getString(R.string.email_is_not_valid)
            )
        } else {
            _forgetUiState.value = _forgetUiState.value.copy(
                emailError = ""
            )
        }
    }

    private fun addErrorPasswordMessage(){
        val password = _forgetUiState.value.password
        val reEnterPassword = _forgetUiState.value.reEnterPassword

        if (password.isEmpty()) {
            _forgetUiState.value = _forgetUiState.value.copy(
                passwordError = context.getString(R.string.please_fill_in_password)
            )

        } else if (!checkPassword(password)) {
            _forgetUiState.value = _forgetUiState.value.copy(
                passwordError = context.getString(R.string.password_required)
            )
        } else {
            _forgetUiState.value = _forgetUiState.value.copy(
                passwordError = ""
            )
        }

        Log.d("vuoncog", "Check: ${checkReEnterPassword(password, reEnterPassword)}")
        Log.d("vuoncog", "Password: ${password}")
        Log.d("vuoncog", "ReEnterPassword: ${reEnterPassword}")

        if (reEnterPassword.isEmpty()) {
            _forgetUiState.value = _forgetUiState.value.copy(
                reEnterPasswordError = context.getString(R.string.please_fill_in_re_enter_password)
            )
        } else if (!checkReEnterPassword(password, reEnterPassword)) {
            _forgetUiState.value = _forgetUiState.value.copy(
                reEnterPasswordError = context.getString(R.string.password_don_t_match)
            )
        } else {
            _forgetUiState.value = _forgetUiState.value.copy(
                reEnterPasswordError = ""
            )
        }
    }
}

data class ForgetUiState(
    val email: String = "",
    val emailError: String = "",
    val isLoading: Boolean = false,
    val resend: Boolean = false,
    val otpError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val reEnterPassword: String = "",
    val reEnterPasswordError: String = "",
)