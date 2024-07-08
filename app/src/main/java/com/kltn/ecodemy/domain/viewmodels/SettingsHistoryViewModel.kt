package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.Constant.OWNER_ID
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.payment.PaymentHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsHistoryViewModel @Inject constructor(
    private val ecodemyApi: EcodemyApi,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _settingHistoryUiState: MutableStateFlow<RequestState<SettingsHistoryUiState>> =
        MutableStateFlow(RequestState.Loading)
    val settingsHistoryUiState = _settingHistoryUiState.asStateFlow()

    private val userOwnerId = savedStateHandle.get<String>(OWNER_ID) ?: ""

    init {
        getListPaymentHistory()
        Log.d("OwnerId", userOwnerId)
    }

    private fun getListPaymentHistory() {
        viewModelScope.launch {
            try {
                val data = ecodemyApi.getPayment(userOwnerId)
                _settingHistoryUiState.value = RequestState.Success(
                    data = SettingsHistoryUiState(
                        listPaymentHistory = data
                    )
                )
                Log.d("History", data.toString())
            } catch (e: Exception){
                Log.d("History", e.toString())
                _settingHistoryUiState.value = RequestState.Idle
            }
        }
    }
}

data class SettingsHistoryUiState(
    val listPaymentHistory: List<PaymentHistory> = emptyList()
)