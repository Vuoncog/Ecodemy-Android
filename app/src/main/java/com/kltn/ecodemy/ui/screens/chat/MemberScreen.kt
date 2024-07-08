package com.kltn.ecodemy.ui.screens.chat

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.chat.Member
import com.kltn.ecodemy.domain.models.user.Role
import com.kltn.ecodemy.domain.viewmodels.ChatDetailViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.ProfileTab
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.EndLinear
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.StartLinear

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemberScreen(
    onBackClicked: () -> Unit,
    chatDetailViewModel: ChatDetailViewModel,
) {
    val context = LocalContext.current
    val chatUiState = chatDetailViewModel.chatUiState.collectAsState()
    Log.d("AvatarGroup", chatUiState.value.avatarGroup)
    val imageRequest = ImageRequest.Builder(context)
        .data(chatUiState.value.avatarGroup)
        .error(R.drawable.default_user_image)
        .crossfade(enable = true)
        .allowHardware(enable = false)
        .build()
    val members = chatUiState.value.members.members!!
    val groupName = chatUiState.value.chatTitle
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = BackgroundColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            MemberTopBar(
                onBackClicked = onBackClicked,
            )
        }, content = {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    ProfileTab(
                        imageRequest = imageRequest, profileName = groupName,
                        memberTag = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    EcodemyText(
                        format = Nunito.Title1,
                        data = stringResource(id = R.string.members),
                        color = Neutral2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                top = 12.dp,
                                bottom = 4.dp
                            )
                            .padding(horizontal = Constant.PADDING_SCREEN)
                    )
                }
                items(members) { member ->
                    MemberItem(
                        profileName = member.name!!,
                        imageRequest = chatDetailViewModel.findAvatar(
                            member.name!!,
                            member.ownerId!!,
                            context
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun MemberItem(
    imageRequest: ImageRequest,
    profileName: String,
    paddingValues: PaddingValues = PaddingValues(8.dp),
    isTeacher: Boolean = false,
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = Constant.PADDING_SCREEN)
            .padding(bottom = 4.dp)
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageRequest),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(Constant.HOME_AVATAR_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            EcodemyText(
                data = profileName,
                format = Nunito.Title2,
                color = Neutral1
            )
            if (isTeacher) {
                EcodemyText(
                    data = stringResource(id = R.string.route_teacher),
                    format = Nunito.Subtitle1,
                    color = Role.Teacher.color
                )
            }
        }
    }
}

@Composable
fun MemberTopBar(
    onBackClicked: () -> Unit,
    height: Dp = 56.dp
) {
    Row(
        modifier = Modifier
            .height(height)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(StartLinear, EndLinear),
                    start = Offset.Infinite,
                    end = Offset.Zero
                )
            )
            .padding(
                end = 4.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                contentDescription = "Back",
                tint = Neutral1
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.members),
                style = Nunito.Heading2.textStyle,
                color = Neutral1,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.size(Constant.ICON_INTERACTIVE_SIZE))
    }
}
