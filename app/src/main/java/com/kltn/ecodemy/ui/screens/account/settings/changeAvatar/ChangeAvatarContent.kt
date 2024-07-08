package com.kltn.ecodemy.ui.screens.account.settings.changeAvatar

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoOutlinedButton
import com.kltn.ecodemy.ui.screens.account.ACCOUNT_AVATAR_SIZE
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun ChangeAvatarContent(
    paddingValues: PaddingValues,
    imageRequest: ImageRequest,
    onChooseClicked: (Uri?) -> Unit,
    onCaptureClicked: () -> Unit,
) {
    val contract =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            onChooseClicked(it)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    top = 20.dp,
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(ACCOUNT_AVATAR_SIZE)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
                .background(Color.White)
        )
        EcodemyText(
            format = Nunito.Title2,
            data = stringResource(R.string.your_current_avatar),
            color = Neutral4,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    bottom = 12.dp
                ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))

        EcodemyText(
            format = Nunito.Subtitle1,
            data = stringResource(id = R.string.change_avatar),
            color = Neutral2,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp

                ),
            textAlign = TextAlign.Start
        )
        KocoOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    bottom = 20.dp,
                )
                .padding(
                    horizontal = 16.dp
                ),
            textContent = stringResource(R.string.choose_from_library),
            icon = R.drawable.file_image,
            onClick = {
                contract.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )
        KocoOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    bottom = 20.dp,
                )
                .padding(
                    horizontal = 16.dp
                ),
            textContent = stringResource(R.string.capture_the_photo),
            icon = R.drawable.camera,
            onClick = onCaptureClicked
        )
    }
}