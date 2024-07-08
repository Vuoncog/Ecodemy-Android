package com.kltn.ecodemy.ui.screens.course.review

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.viewmodels.ReviewViewModel
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.screens.account.settings.SettingsTopBar
import com.kltn.ecodemy.ui.theme.Primary1
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun ReviewScreen(
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    val reviewUiState = reviewViewModel.reviewUiState.collectAsState()
    val title = reviewViewModel.title.collectAsState()
    val content = reviewViewModel.content.collectAsState()
    val edit = reviewUiState.value.review != null
    val rate = remember {
        mutableIntStateOf(5)
    }
    val isLoading = remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            SettingsTopBar(
                onBackClicked = onBackClicked,
                title = stringResource(id = R.string.review)
            )
        },
        bottomBar = {
            KocoButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(
                        horizontal = Constant.PADDING_SCREEN,
                        vertical = 20.dp
                    ),
                textContent = stringResource(R.string.send_review), icon = null,
                onClick = {
                    isLoading.value = true
                    if (!edit) {
                        Log.d("RunPost", "Run")
                        reviewViewModel.postReview(rate = rate.intValue,
                            onFailed = {
                                isLoading.value = false
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.title_or_content_is_not_empty),
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.thank_you_for_your_review),
                                    Toast.LENGTH_SHORT
                                ).show()
                                onBackClicked()
                            }
                        )
                    } else {
                        Log.d("RunEdit", "Run")
                        reviewViewModel.editReview(rate = rate.intValue,
                            onFailed = {
                                isLoading.value = false
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.title_or_content_is_not_empty),
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.thank_you_for_your_review),
                                    Toast.LENGTH_SHORT
                                ).show()
                                onBackClicked()
                            }
                        )
                    }
                }
            )
        }
    ) {
        ReviewContent(
            paddingValues = it,
            title = title.value,
            titleError = reviewUiState.value.titleError,
            onTitleChanged = reviewViewModel::setTitle,
            content = content.value,
            contentError = reviewUiState.value.contentError,
            onContentChanged = reviewViewModel::setContent,
            rate = rate
        )
    }

    if (isLoading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.4f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Primary1)
        }
    }
}