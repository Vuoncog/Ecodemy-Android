package com.kltn.ecodemy.ui.screens.chat

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.chat.Chat
import com.kltn.ecodemy.domain.models.chat.LatestMessage
import com.kltn.ecodemy.domain.viewmodels.ChatListViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

private val CHAT_HEADER_BACKGROUND_HEIGHT = 56.dp
private val CHAT_THUMBNAIL_SIZE = 40.dp

@Composable
fun ChatListScreen(
    onBackClicked: () -> Unit,
    onItemClicked: (chatTitle: String, imageUrl: String) -> Unit,
    chatListViewModel: ChatListViewModel = hiltViewModel(),
) {
    val chatListUiState = chatListViewModel.chatListUiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        topBar = {
            ChatTopBar(
                onBackClicked = onBackClicked,
                height = CHAT_HEADER_BACKGROUND_HEIGHT,
            )
        },
        content = { paddingValues ->
            ChatListContent(
                paddingValues = paddingValues,
                onItemClicked = onItemClicked,
                chatList = chatListUiState.value.chatList,
                getAvatar = chatListViewModel::getAvatarImageLink
            )
        }
    )
}

@Composable
fun ChatListContent(
    paddingValues: PaddingValues,
    onItemClicked: (chatTitle: String, imageUrl: String) -> Unit,
    chatList: List<Chat>,
    getAvatar: (avatarPath: String, context: Context) -> ImageRequest,
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        items(chatList) {
            ChatItem(
                onItemClicked = onItemClicked,
                chatTitle = it.chatTitle!!,
                latestMessage = it.latestMessage!!,
                isLast = it == chatList.last(),
                imageRequest = getAvatar(it.latestMessage?.avatar!!, context)
            )
        }
    }
}

@Composable
fun ChatItem(
    onItemClicked: (chatTitle: String, imageUrl: String) -> Unit,
    chatTitle: String,
    latestMessage: LatestMessage,
    isLast: Boolean = false,
    imageRequest: ImageRequest,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                onItemClicked(
                    chatTitle,
                    latestMessage.avatar!!
                )
            }
            .padding(
                start = Constant.PADDING_SCREEN,
                end = Constant.PADDING_SCREEN
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Chat user",
            modifier = Modifier
                .padding(
                    top = 12.dp
                )
                .size(CHAT_THUMBNAIL_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(
                top = 12.dp
            )
        ) {
            EcodemyText(
                format = Nunito.Title1,
                data = chatTitle,
                color = Neutral1
            )
            EcodemyText(
                format = Nunito.Subtitle3,
                data = latestMessage.latestMessageToString(),
                color = Neutral2
            )
            if (!isLast) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    thickness = 1.dp,
                    color = BackgroundColor
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(Color.White),
                )
            }
        }
    }
}

private fun LatestMessage.latestMessageToString() =
    "${this.member}: ${this.message}"