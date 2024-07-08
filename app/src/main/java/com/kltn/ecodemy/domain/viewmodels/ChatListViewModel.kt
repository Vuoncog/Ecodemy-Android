package com.kltn.ecodemy.domain.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.domain.models.chat.Chat
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val firebaseDataProcess: FirebaseDataProcess,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    private val _chatListUiState = MutableStateFlow(ChatListUiState())
    val chatListUiState = _chatListUiState.asStateFlow()

    init {
        getOwnerId()
        Log.d("ChatList", _chatListUiState.value.chatList.toString())
    }

    private fun getOwnerId() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.refreshUser(coerce = true)
            authenticationRepository.getUser {
                setChatList(ownerId = it.ownerId)
            }
        }
    }

    fun getAvatarImageLink(
        avatarPath: String,
        context: Context,
    ): ImageRequest {
        Log.d("AvatarGroup", avatarPath)
        return ImageRequest.Builder(context)
            .data(avatarPath)
            .error(R.drawable.default_user_image)
            .crossfade(enable = true)
            .allowHardware(enable = false)
            .build()
    }

    private fun setChatList(
        ownerId: String,
    ) {
        val chatList = _chatListUiState.value.chatList.toMutableList()
        firebaseDataProcess.getChats(
            ownerId = ownerId,
            onSuccess = {
                Log.d("Chat", it.toString())
                chatList.add(it)
                _chatListUiState.value = _chatListUiState.value.copy(
                    chatList = chatList.groupBy { element ->
                        element.chatTitle
                    }.values.map { group -> group.last() }
                )
            }
        )
    }
}

data class ChatListUiState(
    val chatList: List<Chat> = listOf()
)