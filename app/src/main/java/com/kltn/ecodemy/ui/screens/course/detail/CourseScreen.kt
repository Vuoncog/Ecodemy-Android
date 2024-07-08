package com.kltn.ecodemy.ui.screens.course.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.viewmodels.CourseViewModel
import com.kltn.ecodemy.ui.screens.state.EmptyScreen
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Primary1
import kotlin.math.roundToInt

@Composable
fun CourseScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    onLoginClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onLessonItemClicked: (Int, Int) -> Unit = { _, _ -> },
    onPurchasedClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onChatToConsultant: () -> Unit,
    onRatedClicked: (String) -> Unit,
) {
    val uiState = viewModel.courseUiState.collectAsState()
    val course = uiState.value
    val firstCourse = viewModel.firstCourse
    val userReview = viewModel.userReview

    val bottomBarHeight = 64.dp
    val bottomBarHeightPx = with(LocalDensity.current) { bottomBarHeight.roundToPx().toFloat() }
    val bottomBarOffsetHeightPx = remember { mutableFloatStateOf(bottomBarHeightPx) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.floatValue + delta
                bottomBarOffsetHeightPx.floatValue =
                    newOffset.coerceIn(0f, bottomBarHeightPx)

                return Offset.Zero
            }
        }
    }

    when (course) {
        is RequestState.Success -> {
            Scaffold(
                modifier = Modifier.nestedScroll(nestedScrollConnection),
                containerColor = BackgroundColor,
                bottomBar = {
                    CourseBottomBar(
                        modifier = Modifier
                            .height(bottomBarHeight)
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = bottomBarOffsetHeightPx.floatValue.roundToInt()
                                )
                            },
                        price = if (course.data.course.salePrice == 0.0) course.data.course.price
                        else course.data.course.salePrice,
                        isLogout = viewModel.firstCourse.value == null,
                        onEnrollClicked = {
                            if (course.data.course.type) {
                                onPurchasedClicked()
                            }
                        },
                        courseType = course.data.course.type,
                        onChatToConsultant = onChatToConsultant,
                        isPurchased = course.data.course.purchaseStatus
                    )
                }
            ) { paddingValues ->
                CourseContent(
                    paddingValues = paddingValues,
                    course = course.data.course,
                    reviews = course.data.reviews,
                    firstCourse = firstCourse.value,
                    userReview = userReview.value,
                    isLogout = viewModel.firstCourse.value == null,
                    onWishlistClicked = {
                        viewModel.updateWishlist(
                            course.data.course._id,
                            course.data.course.wishlistStatus
                        )
                    },
                    onPurchaseClicked = onPurchasedClicked,
                    onLoginClicked = onLoginClicked,
                    onBackClicked = onBackClicked,
                    onLessonItemClicked = onLessonItemClicked,
                    onChatToConsultant = onChatToConsultant,
                    onRegisterClicked = onRegisterClicked,
                    onRatedClicked = {
                        onRatedClicked(viewModel.getStudentId())
                    }
                )
            }
        }

        is RequestState.Idle -> {
            EmptyScreen()
        }

        is RequestState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary1)
            }
        }

        else -> {}
    }
}