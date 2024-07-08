package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.TextFieldValidationRepository
import com.kltn.ecodemy.domain.models.user.Role
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val textFieldValidationRepository: TextFieldValidationRepository,
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()
    val currentTime = System.currentTimeMillis()

    fun updateEmail(email: String) {
        _loginUiState.value = _loginUiState.value.copy(
            email = email
        )
    }

    fun updatePassword(password: String) {
        _loginUiState.value = _loginUiState.value.copy(
            password = password
        )
    }

    fun updateHidePassword() {
        val hidePassword = loginUiState.value.hidePassword
        _loginUiState.value = _loginUiState.value.copy(
            hidePassword = !hidePassword
        )
    }

    fun ce() {
        val current = System.currentTimeMillis()
        Log.d("vuoncog", "current: ${current - currentTime}")
        Log.d("vuoncog", "available: ${current + (60000)}")
    }

    fun loginAccount(
        onSuccess: (Boolean) -> Unit,
    ) {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password
        val emailValidation = checkMail(email = email)
        viewModelScope.launch {
            try {
                if (emailValidation) {
                    if (checkPassword(password)) {
                        _loginUiState.value = _loginUiState.value.copy(
                            errorMessage = "",
                            isEmailWrong = false,
                            isPasswordWrong = false,
                        )
                        withContext(Dispatchers.Main) {
                            val isLogged =
                                authenticationRepository.loginByEmailPassword(email, password)
                            if (isLogged) {
                                authenticationRepository.refreshUser(coerce = true)
                                authenticationRepository.getUser {
                                    Log.d("User", it.toString())
                                    if (it.role == Role.Teacher) {
                                        onSuccess(true)
                                    } else {
                                        onSuccess(false)
                                    }
                                }
                                _loginUiState.value = _loginUiState.value.copy(
                                    isLogged = isLogged
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ErrorLog", e.toString())
                _loginUiState.value = _loginUiState.value.copy(
                    errorMessage = "Email/Password is invalid",
                    isEmailWrong = true,
                    isPasswordWrong = true,
                )
            }
        }
    }

    private fun checkMail(email: String): Boolean {
        if (email.isNotEmpty()) {
            if (textFieldValidationRepository.checkMail(email)) {
                _loginUiState.value = _loginUiState.value.copy(
                    isEmailWrong = !textFieldValidationRepository.checkMail(email),
                    errorMessage = "Email is invalid"
                )
            } else {
                _loginUiState.value = _loginUiState.value.copy(
                    isEmailWrong = false,
                    errorMessage = ""
                )
            }
        } else {
            _loginUiState.value = _loginUiState.value.copy(
                isEmailWrong = true,
                errorMessage = "Please enter email"
            )
        }
        return textFieldValidationRepository.checkMail(email)
    }

    private fun checkPassword(password: String): Boolean {
        return if (password.isEmpty()) {
            _loginUiState.value = _loginUiState.value.copy(
                isPasswordWrong = true,
                errorMessage = "Please enter password"
            )
            false
        } else {
            true
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val isEmailWrong: Boolean = false,
    val password: String = "",
    val isPasswordWrong: Boolean = false,
    val hidePassword: Boolean = true,
    val rememberMe: Boolean = true,
    val errorMessage: String = "",
    val isLogged: Boolean = false,
)