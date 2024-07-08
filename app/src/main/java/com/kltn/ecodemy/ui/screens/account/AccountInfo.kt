package com.kltn.ecodemy.ui.screens.account

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.user.Role
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

val ACCOUNT_AVATAR_SIZE = 128.dp

@Composable
fun AccountInfo(
    context: Context = LocalContext.current,
    usrName: String,
    usrGmail: String?,
    role: String,
    avatar: String = "",
) {
    val userRole = Role.of(role) ?: Role.Guide

    val imageRequest = ImageRequest.Builder(context = context)
        .data(avatar)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                top = 20.dp,
                bottom = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageRequest, contentDescription = "Avatar",
            modifier = Modifier
                .size(ACCOUNT_AVATAR_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EcodemyText(
                format = Nunito.Title1,
                data = userRole.name,
                color = userRole.color,
                textAlign = TextAlign.Center,
            )
                EcodemyText(
                    format = Nunito.Heading1,
                    data = usrName,
                    color = Neutral1,
                    textAlign = TextAlign.Center,
                )
        }
        if (usrGmail != null){
            EcodemyText(
                format = Nunito.Title2,
                data = usrGmail,
                color = Neutral2,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountHeaderPrev() {
    AccountInfo(
        usrName = "Nguyen Van Vuong",
        usrGmail = "20521667@gmail.com",
        role = "Student",
        avatar = ""
    )
}