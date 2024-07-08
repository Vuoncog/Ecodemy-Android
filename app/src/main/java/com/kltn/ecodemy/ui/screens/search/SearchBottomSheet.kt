package com.kltn.ecodemy.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.course.Keyword
import com.kltn.ecodemy.domain.viewmodels.SearchViewModel
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.Primary2

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchBottomSheet(
    keywords: List<Keyword>,
    checkedFilter: String,
    selectedKeyword: MutableMap<String, Pair<Boolean,String>>,
    resetFilterKeywords: () -> Unit,
//    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    if (checkedFilter.isNotEmpty()) {
        val listCheckedFilter = checkedFilter.split(",")
        keywords.forEach { keyword ->
            if (listCheckedFilter.contains(keyword._id)) {
                selectedKeyword[keyword._id] = Pair(true, keyword._id)
            }
        }
        resetFilterKeywords()
    }
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        EcodemyText(format = Nunito.Title1, data = stringResource(R.string.filter), color = Neutral2)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            ){
            keywords.forEach { keyword ->
                FilterItem(id = keyword._id, text = keyword.name, selectedItems = selectedKeyword)
            }
        }
        Spacer(modifier = Modifier.size(32.dp))
    }
}
@Composable
fun FilterItem(
    id: String,
    text: String,
    selectedItems: MutableMap<String, Pair<Boolean, String>>
) {
    var checkedState by remember { mutableStateOf(selectedItems[id] ?: Pair(false,"")) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckbox(
            checked = checkedState.first,
            onCheckedChange = { checked ->
                checkedState = Pair(checked, id)
                selectedItems[id] = Pair(checked, id)
            },
        )
        EcodemyText(format = Nunito.Title2, data = text, color = Neutral1)
    }
}
@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    borderColorChecked: Color = Primary1,
    borderColorUnchecked: Color = Neutral2,
    borderWidthChecked: Dp = 1.dp,
    borderWidthUnchecked: Dp = 1.5.dp
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (checked) Primary2 else Color.White)
            .border(
                if (checked) borderWidthChecked else borderWidthUnchecked,
                if (checked) borderColorChecked else borderColorUnchecked,
                RoundedCornerShape(6.dp)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.check),
                contentDescription = "Check",
                modifier = Modifier
                    .size(16.dp),
                tint = Primary1
            )
        }
    }
}