package com.kltn.ecodemy.ui.screens.account.settings.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.language.LanguageTag
import com.kltn.ecodemy.domain.viewmodels.SettingsViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.screens.account.settings.SettingsTopBar
import com.kltn.ecodemy.ui.theme.BackgroundColor
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral4
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsLanguageContent(
    onBackClicked: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val supportLang = listOf(
        LanguageTag.VIETNAMESE,
        LanguageTag.ENGLISH
    ).sortedBy { it.langName }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locale = settingsViewModel.languageTag.collectAsState().value

    val isLoading = remember {
        mutableStateOf(false)
    }

    if (!isLoading.value) {
        Scaffold(
            topBar = {
                SettingsTopBar(onBackClicked = onBackClicked)
            },
            containerColor = BackgroundColor
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                itemsIndexed(supportLang) { index, tag ->
                    LanguageItem(tag = tag, isSelected = locale.tag == tag.tag) {
                        scope.launch {
                            isLoading.value = true
                            settingsViewModel.changeLanguage(it, context)
                            delay(1000)
                            isLoading.value = false
                        }
                    }
                    if (index != supportLang.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = Neutral4
                        )
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(32.dp)
            ) {
                CircularProgressIndicator(
                    color = Primary1
                )
                Spacer(modifier = Modifier.height(8.dp))
                EcodemyText(
                    format = Nunito.Title2,
                    data = stringResource(R.string.converting_to, locale.langName),
                    color = Neutral2
                )
            }
        }
    }
}