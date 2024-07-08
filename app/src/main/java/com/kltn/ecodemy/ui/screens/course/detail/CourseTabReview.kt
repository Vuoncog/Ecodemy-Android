package com.kltn.ecodemy.ui.screens.course.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.Review
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.components.KocoOutlinedButton
import com.kltn.ecodemy.ui.screens.search.CourseRating
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun RateCourse(
    isRated: Boolean,
    onRateClicked: () -> Unit,
) {
    Log.d("Rate", isRated.toString())
    val title =
        if (isRated) stringResource(R.string.you_already_rated_this_course) else stringResource(
            R.string.you_can_rate_this_course
        )

    val buttonContext =
        if (isRated) stringResource(R.string.edit_review) else stringResource(R.string.rate_course)
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EcodemyText(format = Nunito.Title1, data = title, color = Neutral3)
        KocoOutlinedButton(
            textContent = buttonContext, icon = null,
            onClick = onRateClicked
        )
    }
}

@Composable
fun CourseTabReviewItem(
    paddingValues: PaddingValues,
    review: Review
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                horizontal = 20.dp,
            )
            .padding(paddingValues)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ReviewItemUser(
            user = review.userData!!,
            rate = review.rate
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Neutral4
        )

        EcodemyText(format = Nunito.Title2, data = review.title, color = Neutral2)
        EcodemyText(format = Nunito.Body, data = review.content, color = Neutral1)
    }
}

@Composable
fun ReviewItemUser(
    user: User,
    rate: Int,
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context = context)
        .data(user.userInfo.avatar)
        .placeholder(R.drawable.default_user_image)
        .error(R.drawable.default_user_image)
        .crossfade(true)
        .allowHardware(false)
        .build()

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            EcodemyText(
                format = Nunito.Subtitle3,
                data = user.userInfo.fullName,
                color = Neutral1
            )

            CourseRating(rating = rate.toDouble())
        }
    }
}