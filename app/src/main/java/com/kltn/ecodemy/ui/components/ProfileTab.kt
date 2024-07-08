package com.kltn.ecodemy.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

private val TEACHER_PROFILE_PADDING = PaddingValues(
    vertical = 20.dp,
    horizontal = 12.dp
)

private val TEACHER_PROFILE_SPACE_BETWEEN = Arrangement.spacedBy(12.dp)
private val TEACHER_PROFILE_IMAGE_SIZE = 96.dp

@Composable
fun ProfileTab(
    context: Context = LocalContext.current,
    image: String,
    profileName: String,
    job: String = "",
    memberTag: Boolean = false,
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .data(image)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(TEACHER_PROFILE_PADDING),
        verticalArrangement = TEACHER_PROFILE_SPACE_BETWEEN,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Teacher profile",
            modifier = Modifier
                .size(TEACHER_PROFILE_IMAGE_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EcodemyText(
                format = Nunito.Heading1,
                data = profileName,
                color = Neutral1
            )
            if (!memberTag) {
                EcodemyText(
                    format = Nunito.Title2,
                    data = job,
                    color = Neutral2
                )
            }
        }
    }
}

@Composable
fun ProfileTab(
    imageRequest: ImageRequest,
    profileName: String,
    job: String = "",
    memberTag: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(TEACHER_PROFILE_PADDING),
        verticalArrangement = TEACHER_PROFILE_SPACE_BETWEEN,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Teacher profile",
            modifier = Modifier
                .size(TEACHER_PROFILE_IMAGE_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EcodemyText(
                format = Nunito.Heading1,
                data = profileName,
                color = Neutral1
            )
            if (!memberTag) {
                EcodemyText(
                    format = Nunito.Title2,
                    data = job,
                    color = Neutral2
                )
            }
        }
    }
}

@Preview
@Composable
fun TeacherProfilePrev() {
    ProfileTab(image = "", profileName = "Nguyen Van Vuong", job = "Android")
}