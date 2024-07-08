package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.ErrorMessage.NON_LOGIN
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.LearnRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val learnRepository: LearnRepository,
) : ViewModel() {
    private val _learnUiState: MutableStateFlow<RequestState<LearnUiState>> =
        MutableStateFlow(RequestState.Loading)
    val learnUiState = _learnUiState.asStateFlow()
    private val data: MutableState<LearnUiState> = mutableStateOf(LearnUiState())

    private val _isLogged = mutableStateOf(false)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            authenticationRepository.refreshUser(coerce = true)
            authenticationRepository.getUser {
//                _ownerId.value = it.ownerId
                checkIsLogged(it.ownerId)
            }
        }
    }

    private fun checkIsLogged(ownerId: String) {
        viewModelScope.launch {
            try {
                if (!authenticationRepository.isLogged()) {
                    _learnUiState.value = RequestState.Error(Exception(NON_LOGIN))
                    return@launch
                }
                if (authenticationRepository.isLogged() != _isLogged.value) {
                    _isLogged.value = authenticationRepository.isLogged()
                    if (authenticationRepository.isLogged()) {
                        _learnUiState.value = RequestState.Loading
                    }
                    fetchLearnData(ownerId)
                } else { //check if learn is updated
                    val currentLearn: MutableState<List<Course>> = mutableStateOf(emptyList())
                    learnRepository.getLearn(ownerId) {
                        currentLearn.value = it
                    }
                    if (currentLearn.value != data.value.courses) {
                        _learnUiState.value = RequestState.Loading
                        fetchLearnData(ownerId)
                    }
                }
            } catch (e: Exception) {
                _learnUiState.value = RequestState.Idle
            }
        }
    }

    private suspend fun fetchLearnData(ownerId: String) {
        try {
            if (_isLogged.value && ownerId.isNotBlank()) {
                learnRepository.getLearn(ownerId = ownerId) {
                    data.value = data.value.copy(
                        courses = it
                    )
                }
                setAllLearn()
            } else {
                data.value = LearnUiState()
                _learnUiState.value = RequestState.Idle
            }
        } catch (e: Exception) {
            data.value = LearnUiState()
            _learnUiState.value = RequestState.Idle
        }
    }

    private fun setAllLearn() {
        if (data.value != LearnUiState()) {
            _learnUiState.value = RequestState.Success(
                data = data.value
            )
        } else {
            _learnUiState.value = RequestState.Idle
        }
    }
}

data class LearnUiState(
    val courses: List<Course> = emptyList(),
)