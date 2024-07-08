package com.kltn.ecodemy.domain.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kltn.ecodemy.constant.ErrorMessage.NON_LOGIN
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.data.repository.WishlistRepository
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val wishlistRepository: WishlistRepository,
) : ViewModel() {
    private val _wishlistUiState: MutableStateFlow<RequestState<WishlistUiState>> =
        MutableStateFlow(RequestState.Loading)
    val wishlistUiState = _wishlistUiState.asStateFlow()
    private val data: MutableState<WishlistUiState> = mutableStateOf(WishlistUiState())

    private val _isLogged = mutableStateOf(false)
    private val _ownerId = mutableStateOf("")

    init {
        refresh()
    }

    fun refresh(){
        viewModelScope.launch {
            authenticationRepository.refreshUser(coerce = true)
            authenticationRepository.getUser {
                _ownerId.value = it.ownerId
                checkIsLogged(it.ownerId)
            }
        }
    }

    private fun checkIsLogged(ownerId: String) {
        Log.d("WLOld", _isLogged.value.toString())
        Log.d("WLNew", authenticationRepository.isLogged().toString())
        viewModelScope.launch {
            if (!authenticationRepository.isLogged()){
                _wishlistUiState.value = RequestState.Error(Exception(NON_LOGIN))
                return@launch
            }
            if (authenticationRepository.isLogged() != _isLogged.value){
                _isLogged.value = authenticationRepository.isLogged()
                if (authenticationRepository.isLogged()) {
                    _wishlistUiState.value = RequestState.Loading
                }
                fetchWishlistData(ownerId)
            }
            else { //check if wishlist is updated
                val currentWishlist: MutableState<List<Course>> = mutableStateOf(emptyList())
                wishlistRepository.getWishlist(ownerId) {
                    currentWishlist.value = it
                }
                if (currentWishlist.value != data.value.courses) {
                    _wishlistUiState.value = RequestState.Loading
                    fetchWishlistData(ownerId)
                }
            }
        }
    }

    private suspend fun fetchWishlistData(ownerId: String) {
        if (_isLogged.value && ownerId.isNotBlank()) {
            wishlistRepository.getWishlist(ownerId = ownerId) {
                data.value = data.value.copy(
                    courses = it
                )
                setAllWishlist()
            }
        } else {
            data.value = WishlistUiState()
            _wishlistUiState.value = RequestState.Idle
        }
    }

    fun removeWishlist(courseId: String) {
        viewModelScope.launch {
            try {
                wishlistRepository.updateWishlist(_ownerId.value, courseId, "remove") {
//                    val respone = it
                }
                fetchWishlistData(_ownerId.value)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }

    private fun setAllWishlist() {
        if (data.value != WishlistUiState()) {
            _wishlistUiState.value = RequestState.Success(
                data = data.value
            )
        } else {
            _wishlistUiState.value = RequestState.Idle
        }
    }
}

data class WishlistUiState(
    val courses: List<Course> = emptyList(),
)