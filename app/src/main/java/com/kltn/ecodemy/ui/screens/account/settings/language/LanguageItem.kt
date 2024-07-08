package com.kltn.ecodemy.ui.screens.account.settings.language

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.language.LanguageTag
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1

@SuppressLint("ResourceType")
@Composable
fun LanguageItem(
    tag: LanguageTag,
    isSelected: Boolean,
    onClicked: (LanguageTag) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable {
                onClicked(tag)
            }
            .background(Color.White)
            .padding(
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = 8.dp
                ),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            EcodemyText(format = Nunito.Title2, data = tag.langName, color = Neutral1)
            EcodemyText(
                format = Nunito.Subtitle3,
                data = stringResource(id = tag.systemLangName),
                color = Neutral2
            )
        }
        if (isSelected){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.check), contentDescription = null,
                tint = Primary1,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}