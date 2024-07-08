package com.kltn.ecodemy.constant

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.kltn.ecodemy.ui.theme.colorStops
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun String.fromLinkToUrlArgument() = this.replace(oldChar = '/', newChar = '@')
fun String.fromUrlArgumentToLink() = this.replace(oldChar = '@', newChar = '/')

fun Int.lessonIndex(sectionIndex: Int) = this * sectionIndex + sectionIndex
fun Int.lessonPosition(sectionIndex: Int) = (this - sectionIndex) / sectionIndex

@RequiresApi(Build.VERSION_CODES.O)
fun Long.epochSecondToLocalDateTime(): LocalDateTime =
    LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.formatToString(): String {
    val formatter = DateTimeFormatter.ofPattern(Constant.PATTERN)
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.formatToMessageTimeString(): String {
    val formatter = DateTimeFormatter.ofPattern(Constant.MESSAGE_PATTERN)
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.formatToMessageDateString(): String {
    val formatter = DateTimeFormatter.ofPattern(Constant.MESSAGE_DATE_PATTERN)
    return this.format(formatter)
}

fun Modifier.textFieldBorder(
    color: Color,
) = this then Modifier.border(
    width = 1.dp,
    color = color,
    shape = RoundedCornerShape(12.dp)
)


fun moveDown(
    state: ScrollState,
    scope: CoroutineScope,
    focusManager: FocusManager
) = Pair(
    KeyboardActions(
        onNext = {
            focusManager.moveFocus(FocusDirection.Down)
            scope.launch {
                state.animateScrollTo(
                    state.maxValue,
                    animationSpec = tween(300, 0, LinearOutSlowInEasing)
                )
            }
        }
    ),
    KeyboardOptions(imeAction = ImeAction.Next)
)

fun Modifier.authBackground() =
    this then Modifier.background(
        brush = Brush.verticalGradient(
            colorStops = colorStops
        )
    )

fun Modifier.topBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        this then Modifier.drawBehind {
            val width = size.width

            drawLine(
                color = color,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = width, y = 0f),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.clickableWithoutRippleEffect(
    enabled: Boolean,
    onClick: () -> Unit,
) =
    composed(
        factory = {
            this then Modifier.clickable(
                onClick = onClick,
                enabled = enabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
        }
    )


@Composable
fun shimmerEffect(): Float {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    return alphaAnim
}

fun Context.getActivity(): Activity = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> getActivity()
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = this.destination.parent?.route?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

//fun scaleImage(image: Drawable?, scaleFactor: Float): Drawable? {
//    if (image == null || image !is BitmapDrawable) {
//        return image
//    }
//    val b = image.bitmap
//    val sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor)
//    val sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor)
//    val bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false)
//    return BitmapDrawable(Resources.getSystem(), bitmapResized)
//}