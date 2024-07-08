package com.kltn.ecodemy.ui.screens.chat

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.kltn.ecodemy.domain.models.chat.Message
import java.time.LocalDate

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatContent(
    paddingValues: PaddingValues,
    messageMap: Map<LocalDate, List<Message>>,
    scrollState: LazyListState,
    currentUser: String,
    isGroup: Boolean = false,
    findAvatar: (name: String, ownerId: String, context: Context) -> ImageRequest
) {
    LaunchedEffect(messageMap) {
        scrollState.animateScrollToItem(0)
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues)
            .systemBarsPadding(),
        reverseLayout = true,
        state = scrollState,
    ) {
        messageMap.forEach { (date, messages) ->
            messagesContent(
                listMessage = messages.reversed(),
                currentUser = currentUser,
                isGroup = isGroup,
                findAvatar = findAvatar,
                context = context
            )
            item {
                ChatDate(localDate = date)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun LazyListScope.messagesContent(
    listMessage: List<Message>,
    currentUser: String,
    isGroup: Boolean = false,
    context: Context,
    findAvatar: (name: String, ownerId: String, context: Context) -> ImageRequest
) {
    items(listMessage) {
        if (it.member != null && it.member == currentUser) {
            ReplyLine(
                content = it.message!!,
                epochSecond = it.timestamp!!,
                username = it.member!!,
                isGroup = isGroup,
                imageRequest = findAvatar(it.member!!, it.ownerId!!, context)
            )
        } else {
            ResponseLine(
                content = it.message!!,
                epochSecond = it.timestamp!!,
                username = it.member!!,
                isGroup = isGroup,
                imageRequest = findAvatar(it.member!!, it.ownerId!!, context)
            )
        }
    }
    item {
        Spacer(modifier = Modifier.height(12.dp))
    }
}