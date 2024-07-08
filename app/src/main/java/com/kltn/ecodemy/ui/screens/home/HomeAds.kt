package com.kltn.ecodemy.ui.screens.home

import android.content.Context
import android.util.Size
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.domain.viewmodels.Ads
import kotlinx.coroutines.delay


@ExperimentalFoundationApi
@Composable
fun HomeAds(
    context: Context = LocalContext.current,
    listAds: List<Ads>,
    onCardClicked: (String) -> Unit,
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .error(R.drawable.ecodemy_logo)
        .crossfade(true)
        .allowHardware(false)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val homeAdsSize = Size(
        (screenWidth - 32),
        (screenWidth - 32) / 16 * 9
    )
    val pagerState = rememberPagerState {
        Int.MAX_VALUE
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(5000)
        pagerState.scrollToPage((pagerState.currentPage + 1))
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) {
        val ads = listAds[it % listAds.size]
        imageRequest.data(ads.images)
        AsyncImage(
            modifier = Modifier
                .padding(horizontal = Constant.PADDING_SCREEN)
                .padding(bottom = 4.dp)
                .size(
                    width = (homeAdsSize.width).dp,
                    height = (homeAdsSize.height).dp
                )
                .clip(Constant.BORDER_SHAPE)
                .graphicsLayer {
                    alpha = lerp(1f, 0f, 0f)
                }
                .clickableWithoutRippleEffect(true) {
                    onCardClicked(ads.courseId)
                },
            model = imageRequest.build(),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}