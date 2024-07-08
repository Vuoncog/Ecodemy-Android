package com.kltn.ecodemy.ui.screens.lesson

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.theme.Primary1

@OptIn(UnstableApi::class)
@Composable
fun LessonVideoPlayer(
    videoUri: String,
    onBackClicked: () -> Unit,
) {
    if (videoUri != "no video") {
        val mediaItem = MediaItem.fromUri(videoUri)
        VideoPlayer(
            mediaItem = mediaItem,
            onBackClicked = onBackClicked
        )
    } else {
        Box(
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .background(Color.Black.copy(0.95f))
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Primary1)
        }
    }

}

@OptIn(UnstableApi::class)
@SuppressLint("OpaqueUnitKey")
@Composable
fun VideoPlayer(
    mediaItem: MediaItem,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    exoPlayer.setMediaItem(mediaItem)
    val lifeCycleOwner = LocalLifecycleOwner.current
    val pausing = remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.release()
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .aspectRatio(16f / 9f)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    exoPlayer.prepare()
                    exoPlayer.addListener(pausing)
                    exoPlayer.playWhenReady = true
                    player = exoPlayer
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                        pausing.value = false
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                        pausing.value = true
                    }

                    else -> {
                        pausing.value = true
                    }
                }
            }
        )
        if (pausing.value) {
            BackButtonPlayer(onClicked = onBackClicked)
        }
    }
}

@Composable
fun BackButtonPlayer(
    onClicked: () -> Unit,
) {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
        contentDescription = "Back Button Player",
        tint = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .clip(CircleShape)
            .clickable {
                onClicked()
            }
            .background(Color.White.copy(alpha = 0.4f))
            .border(1.dp, Color.White.copy(0.7f), CircleShape)
            .padding(4.dp)
            .size(20.dp)
            .rotate(180f)
    )
}

private fun ExoPlayer.addListener(
    pausing: MutableState<Boolean>
) {
    this.addListener(
        object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {// Not playing because playback is paused, ended, suppressed, or the player
                // is buffering, stopped or failed. Check player.playWhenReady,
                // player.playbackState, player.playbackSuppressionReason and
                // player.playerError for details.
                // Active playback.
                pausing.value = !isPlaying
            }
        }
    )
}

@Preview
@Composable
private fun BackPrev() {
    BackButtonPlayer(onClicked = {})
}