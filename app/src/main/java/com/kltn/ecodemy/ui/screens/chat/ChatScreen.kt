package com.kltn.ecodemy.ui.screens.chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.domain.viewmodels.ChatDetailViewModel
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    onBackClicked: () -> Unit,
    onListClicked: () -> Unit,
    chatDetailViewModel: ChatDetailViewModel = hiltViewModel()
) {
    val chatUiState = chatDetailViewModel.chatUiState.collectAsState()
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val belongTo = chatUiState.value.belongTo
    val isLoading = chatUiState.value.isLoading

    if (!isLoading) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            containerColor = BackgroundColor,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                ChatTopBar(
                    onBackClicked = onBackClicked,
                    onListClicked = onListClicked,
                    user = chatUiState.value.chatTitle
                )
            },
            bottomBar = {
                if (belongTo) {
                    ChatBottom(
                        onSendClicked = {
                            chatDetailViewModel.onSend()
                        },
                        value = chatUiState.value.messageEdited,
                        onValueChanged = chatDetailViewModel::onMessageEdited,
                        onTextFieldClicked = {
                            scope.launch {
                                delay(200)
                                scrollState.animateScrollToItem(0)
                            }
                        },
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "You don't belong to this chat",
                            style = Nunito.Subtitle1.textStyle,
                            color = Neutral2
                        )
                    }
                }
            },
            content = { paddingValues ->
                ChatContent(
                    paddingValues = paddingValues,
                    messageMap = chatUiState.value.messagesMap,
                    scrollState = scrollState,
                    currentUser = chatUiState.value.userName,
                    isGroup = chatUiState.value.isGroup,
                    findAvatar = chatDetailViewModel::findAvatar
                )
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Primary1
            )
        }
    }
}