package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.TextFieldValidationRepository
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.user.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val textFieldValidationRepository: TextFieldValidationRepository,
) : ViewModel() {
    private val _accountUiState = MutableStateFlow(
        AccountUiState(
            systemSettingsList = listOf(
                Route.Setting.SettingItem.Language,
            ),
            accountSettingsList = listOf(
                Route.Setting.SettingItem.ChangePassword,
                Route.Setting.SettingItem.ChangeAvatar,
            )
        )
    )
    val accountUiState = _accountUiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh(){
        viewModelScope.launch(Dispatchers.Main) {
            authenticationRepository.refreshUser()
            isLogged()
        }
    }

    private fun setUserInfo(isLogged: Boolean = false) {
        viewModelScope.launch {
            val accountSettingsList = _accountUiState.value.accountSettingsList.toMutableList()
            val paymentHistory = Route.Setting.SettingItem.PaymentHistory()
            authenticationRepository.refreshUser(coerce = true)
            authenticationRepository.getUser {
                if (isLogged) {
                    if (!it._id.isNullOrEmpty()) {
                        paymentHistory.copy(arg = it._id).run {
                            accountSettingsList.add(this)
                        }
                    }
                }
                _accountUiState.value = _accountUiState.value.copy(
                    user = it,
                    email = it.userInfo.email,
                    accountSettingsList = accountSettingsList.distinct()
                )
            }
        }

    }

    fun logout(
        onSuccess: () -> Unit = {},
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                authenticationRepository.logout()
                _accountUiState.value = _accountUiState.value.copy(
                    isLogged = authenticationRepository.isLogged()
                )
                setUserInfo()
                onSuccess()
            }
        }
    }

    fun isLogged() {
        viewModelScope.launch {
            _accountUiState.value = _accountUiState.value.copy(
                isLogged = authenticationRepository.isLogged()
            )
            authenticationRepository.refreshUser()
            setUserInfo(isLogged = true)
        }
    }

    fun updateOldPassword(password: String) {
        _accountUiState.value = _accountUiState.value.copy(
            oldPassword = password
        )
    }

    fun updateNewPassword(password: String) {
        _accountUiState.value = _accountUiState.value.copy(
            newPassword = password
        )
    }

    fun updateReEnterNewPassword(password: String) {
        _accountUiState.value = _accountUiState.value.copy(
            reEnterNewPassword = password
        )
    }

    fun resetPassword(
        onSucceed: () -> Unit,
    ) {
        viewModelScope.launch {
            val oldPassword = _accountUiState.value.oldPassword
            val newPassword = _accountUiState.value.newPassword
            val checkValid = checkValid(newPassword, _accountUiState.value.reEnterNewPassword)
            addErrorMessage()
            if (checkValid) {
                val added = authenticationRepository.resetPassword(oldPassword, newPassword)
                Log.d("bngoc", "status: $added")
                if (added) {
                    onSucceed()
                    logout()
                } else {
                    _accountUiState.value = _accountUiState.value.copy(
                        isChangePasswordComplete = false
                    )
                }
            }
        }
    }

    private fun checkValid(
        newPassword: String,
        reEnterNewPassword: String
    ): Boolean {
        val isPasswordValid = checkNewPassword(newPassword)
        val isReEnterPasswordValid =
            if (newPassword.isNotEmpty()) checkReEnterNewPassword(
                newPassword,
                reEnterNewPassword
            ) else true
        _accountUiState.value = _accountUiState.value.copy(
            isNewPasswordValid = isPasswordValid,
            isReEnterNewPasswordValid = isReEnterPasswordValid,
        )
        return isPasswordValid.and(isReEnterPasswordValid)
    }

    private fun checkNewPassword(password: String) =
        textFieldValidationRepository.checkPassword(password)

    private fun checkReEnterNewPassword(newPassword: String, reEnterNewPassword: String) =
        textFieldValidationRepository.checkReEnterPassword(newPassword, reEnterNewPassword)

    private fun addErrorMessage() {
        val errorMessage = mutableMapOf<String, String>()
        if (_accountUiState.value.newPassword.isEmpty()) {
            errorMessage[Constant.PASSWORD] =
                "Please fill in password"
        } else if (!_accountUiState.value.isNewPasswordValid) {
            errorMessage[Constant.PASSWORD] =
                "Password should be at least 8 characters long and include a mix of letters, numbers, and symbols"
        }

        if (_accountUiState.value.reEnterNewPassword.isEmpty()) {
            errorMessage[Constant.REENTERPASSWORD] =
                "Please fill in re enter password"
        } else if (!_accountUiState.value.isReEnterNewPasswordValid) {
            errorMessage[Constant.REENTERPASSWORD] = "Password don't match"
        }

        _accountUiState.value = _accountUiState.value.copy(
            errorMessage = errorMessage
        )
    }
}

data class AccountUiState(
    val user: User = User(),
    val email: String? = null,
    val role: String = "",
    val ownerId: String? = null,
    val isLogged: Boolean = false,
    val oldPassword: String = "",
    val newPassword: String = "",
    val reEnterNewPassword: String = "",
    val isChangePasswordComplete: Boolean = true,
    val isNewPasswordValid: Boolean = true,
    val isReEnterNewPasswordValid: Boolean = true,
    val errorMessage: Map<String, String> = mapOf(),
    val systemSettingsList: List<Route.Setting.SettingItem>,
    val accountSettingsList: List<Route.Setting.SettingItem>,
)

