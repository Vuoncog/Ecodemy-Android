package com.kltn.ecodemy.domain.viewmodels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import coil.request.ImageRequest
import com.google.firebase.messaging.FirebaseMessaging
import com.kltn.ecodemy.R
import com.kltn.ecodemy.data.api.FirebaseCloudMessagingApi
import com.kltn.ecodemy.data.navigation.Route
import com.kltn.ecodemy.data.repository.AuthenticationRepository
import com.kltn.ecodemy.domain.models.chat.Chat
import com.kltn.ecodemy.domain.models.chat.LatestMessage
import com.kltn.ecodemy.domain.models.chat.Members
import com.kltn.ecodemy.domain.models.chat.Message
import com.kltn.ecodemy.domain.models.fcm.FcmData
import com.kltn.ecodemy.domain.models.fcm.FcmMessage
import com.kltn.ecodemy.domain.models.fcm.FcmNotification
import com.kltn.ecodemy.domain.models.fcm.FirebaseMessage
import com.kltn.ecodemy.domain.repository.AccessToken
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val firebaseDataProcess: FirebaseDataProcess,
    private val authenticationRepository: AuthenticationRepository,
    private val savedStateHandle: SavedStateHandle,
    private val accessToken: AccessToken,
    private val firebaseCloudMessagingApi: FirebaseCloudMessagingApi,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _chatUiState = MutableStateFlow(ChatUiState())
    val chatUiState = _chatUiState.asStateFlow()
    private val chatTitle = savedStateHandle.get<String>(Route.Chat.CHAT_TITLE) ?: "ChatTitle"
    private val imageUrl =
        savedStateHandle.get<String>(Route.Chat.IMAGE_URL) ?: "ImageUrl"

    private lateinit var token: String
    private lateinit var currentUser: String
    private lateinit var ownerId: String
    private lateinit var avatar: String

    init {
        Log.d("AvatarUrl", URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.toString()))
        viewModelScope.launch {
            token = FirebaseMessaging.getInstance().token.await()
            Log.d("Token", token)
            Log.d("AccessToken", accessToken.getAccessToken())
        }
        getOwnerId()
        getAvatarGroup()
    }

    private fun getAvatarGroup() {
        val fakeChat = Chat(
            latestMessage = LatestMessage(
                avatar = imageUrl
            )
        )
        firebaseDataProcess.getAvatarImageUrl(fakeChat) {
            _chatUiState.value = _chatUiState.value.copy(
                avatarGroup = it.latestMessage?.avatar!!,
            )
        }
    }

    fun avatarGroup(context: Context): ImageRequest {
        val avatar = _chatUiState.value.avatarGroup
        return ImageRequest.Builder(context)
            .data(avatar)
            .error(R.drawable.default_user_image)
            .crossfade(enable = true)
            .allowHardware(enable = false)
            .build()
    }

    fun findAvatar(
        name: String,
        ownerId: String,
        context: Context,
    ): ImageRequest {
        val members = _chatUiState.value.members
        val avatar = members.members?.find {
            it.ownerId == ownerId && it.name == name
        }?.avatar ?: ""

        return ImageRequest.Builder(context)
            .data(avatar)
            .error(R.drawable.default_user_image)
            .crossfade(enable = true)
            .allowHardware(enable = false)
            .build()
    }

    private fun getOwnerId() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.refreshUser(coerce = true)
            authenticationRepository.getUser {
                setCurrentUser(it.userInfo.fullName, it.ownerId, it.userInfo.avatar)
                runFirebaseMessagesChildrenNodesListener(
                    ownerId = it.ownerId,
                    chatTitle = chatTitle
                )
                setMembers(
                    ownerId = it.ownerId,
                    chatTitle = chatTitle
                )
            }
        }
    }

    private fun runFirebaseMessagesChildrenNodesListener(
        ownerId: String,
        chatTitle: String,
    ) {
        firebaseDataProcess.messagesChildrenNodes(
            ownerId = ownerId,
            chatTitle = chatTitle,
            onValueChangedListener = {
                getAllMessages(
                    listMessage = it,
                    chatTitle = chatTitle
                )
            },
        )
    }

    private fun setCurrentUser(
        currentUser: String,
        ownerId: String,
        avatar: String,
    ) {
        this.currentUser = currentUser
        this.ownerId = ownerId
        this.avatar = avatar
        _chatUiState.value = _chatUiState.value.copy(
            userName = currentUser
        )
    }

    private fun setMembers(
        ownerId: String,
        chatTitle: String,
    ) {
        firebaseDataProcess.getMembers(
            ownerId = ownerId,
            chatTitle = chatTitle,
            onSuccess = {
                val members = it.members
                _chatUiState.value = _chatUiState.value.copy(
                    isLoading = it.members?.size != members?.size
                )
//                firebaseDataProcess.getAvatarImageUrl(
//                    members = it,
//                    onSuccess = { member ->
//
//                    }
//                )
                areYouBelongTo(
                    ownerId = ownerId,
                    members = it,
                )
            }
        )
    }

    private fun areYouBelongTo(
        ownerId: String,
        members: Members
    ) {
        members.members.also {
            val belongTo = it?.find { member ->
                member.ownerId == ownerId
                        && member.name == this.currentUser
            }
            val new = it?.toMutableList().apply {
                if (belongTo != null) {
                    this?.remove(belongTo)
                    this?.add(
                        belongTo.copy(
                            avatar = avatar,
                            token = token
                        )
                    )
                }
            }
            _chatUiState.value = _chatUiState.value.copy(
                isGroup = (it?.size ?: 2) > 2,
                belongTo = belongTo != null,
                members = members.copy(
                    members = new?.sortedBy { m -> m.name }
                )
            )
        }
    }

    private fun getAllMessages(
        listMessage: List<Message>,
        chatTitle: String
    ) {
        _chatUiState.value = _chatUiState.value.copy(
            chatTitle = chatTitle,
            messagesMap = listMessage.groupBy { message ->
                Instant.ofEpochSecond(message.timestamp!!)
                    .atZone(ZoneId.of("Etc/UTC"))
                    .toLocalDate()
            }
                .toSortedMap(
                    compareByDescending { it }
                )
        )
    }

    fun onMessageEdited(value: String) {
        _chatUiState.value = _chatUiState.value.copy(
            messageEdited = value
        )
    }

    fun onSend() {
        val messageEdited = _chatUiState.value.messageEdited
        val chatTitle = _chatUiState.value.chatTitle
        resetMessageEdited()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val listMess = _chatUiState.value.messagesMap.values.flatten().toMutableList()
            val last = listMess.last()
            listMess.add(
                last.copy(
                    timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                    message = messageEdited,
                    member = this.currentUser,
                    ownerId = this.ownerId
                )
            )
            Log.d("NewList", _chatUiState.value.members.toString())
            firebaseDataProcess.updateData(
                avatarGroup = imageUrl,
                messageList = listMess,
                chatTitle = chatTitle,
                membersList = _chatUiState.value.members.members ?: emptyList()
            )
            _chatUiState.value.members.members?.forEach {
                if (it.token != null && this.currentUser != it.name) {
                    pushNotification(it.token!!, message = messageEdited)
                }
            }
        }
    }

    private fun pushNotification(
        token: String,
        message: String
    ) {
        viewModelScope.launch {
            try {
                val sendMessage = FirebaseMessage(
                    message = FcmMessage(
                        token = token,
                        notification = FcmNotification(
                            body = message,
                            title = context.getString(
                                R.string.send_a_new_message,
                                this@ChatDetailViewModel.currentUser
                            )
                        ),
                        data = FcmData(
                            image = avatar
                        )
                    )
                )

                firebaseCloudMessagingApi.send(
                    auth = "Bearer ${accessToken.getAccessToken()}",
                    firebaseMessage = sendMessage
                )
            } catch (e: HttpException) {
                Log.d("FcmCode", e.response.code.toString())
                throw e
            }
        }
    }

    private fun resetMessageEdited() {
        _chatUiState.value = _chatUiState.value.copy(
            messageEdited = ""
        )
    }

    override fun onCleared() {
        super.onCleared()
        firebaseDataProcess.removeAllListener()
    }
}

data class ChatUiState(
    val messagesMap: Map<LocalDate, List<Message>> = mapOf(),
    val chatTitle: String = "",
    val messageEdited: String = "",
    val index: Int = -1,
    val userName: String = "",
    val members: Members = Members(),
    val belongTo: Boolean = false,
    val isGroup: Boolean = false,
    val avatarGroup: String = "",
    val isLoading: Boolean = true,
)