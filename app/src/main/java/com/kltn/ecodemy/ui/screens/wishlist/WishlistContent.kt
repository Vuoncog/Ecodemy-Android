package com.kltn.ecodemy.ui.screens.wishlist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kltn.ecodemy.constant.ErrorMessage.NON_LOGIN
import com.kltn.ecodemy.domain.models.RequestState
import com.kltn.ecodemy.domain.models.course.Course
import com.kltn.ecodemy.domain.viewmodels.WishlistViewModel
import com.kltn.ecodemy.ui.screens.state.EmptyScreen
import com.kltn.ecodemy.ui.screens.state.NonLoginScreen
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.wireframe.screens.WishlistWireframe

@Composable
fun WishlistContent(
    paddingValues: PaddingValues,
    onCardClicked: (String) -> Unit,
    onLoginClicked: () -> Unit,
    wishlistViewModel: WishlistViewModel,
) {
    val wishlistUiState = wishlistViewModel.wishlistUiState.collectAsState()
    val dataStatus = wishlistUiState.value
    LaunchedEffect(key1 = true) {
        wishlistViewModel.refresh()
    }

    when (dataStatus) {
        is RequestState.Success -> {
            WishlistSuccessfulContent(
                paddingValues = paddingValues,
                onCardClicked = onCardClicked,
                onRemoveClicked = wishlistViewModel::removeWishlist,
                courses = dataStatus.data.courses
            )
        }

        is RequestState.Idle -> {
            EmptyScreen()
        }

        is RequestState.Loading -> {
            WishlistWireframe()
        }

        is RequestState.Error -> {
            if (dataStatus.error.message == NON_LOGIN){
                NonLoginScreen {
                    onLoginClicked()
                }
            }
        }
    }

}

@Composable
fun WishlistSuccessfulContent(
    paddingValues: PaddingValues,
    onCardClicked: (String) -> Unit,
    onRemoveClicked: (String) -> Unit,
    courses: List<Course>,
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(BackgroundColor)
            .fillMaxSize(),
    ) {
        item {
            WishlistHeader()
        }

        items(courses) { course ->
            WishlistCourseCard(
                context = context,
                course = course,
                onCardClicked = onCardClicked,
                onRemoveClicked = onRemoveClicked
            )
        }


    }
}