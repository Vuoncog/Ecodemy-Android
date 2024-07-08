package com.kltn.ecodemy.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.BORDER_SHAPE
import com.kltn.ecodemy.constant.Constant.MINIMUM_INTERACTIVE_SIZE
import com.kltn.ecodemy.ui.theme.ContainerColor
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito

private val SEARCH_BAR_ICON_SIZE = 20.dp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onFilterClicked: () -> Unit,
    modifier: Modifier = Modifier,
    searchKeywords: String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        modifier = Modifier.then(modifier),
        value = value,
        onValueChange = onValueChanged,
        textStyle = Nunito.Subtitle1.textStyle,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                onSearchTriggered("$value,$searchKeywords")
                keyboardController?.hide()
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .height(MINIMUM_INTERACTIVE_SIZE)
                    .clip(BORDER_SHAPE)
                    .background(color = ContainerColor)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.search),
                    contentDescription = "Search",
                    tint = Neutral3,
                    modifier = Modifier.size(SEARCH_BAR_ICON_SIZE)
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "Search",
                            style = Nunito.Subtitle1.textStyle,
                            color = Neutral3
                        )
                    }
                    innerTextField()
                }
                BadgedBox(
                    modifier = Modifier
                        .clickable(onClick = onFilterClicked),
                    badge = {
                        if (searchKeywords != "") {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(
                                        x = (-1).dp,
                                        y = 4.dp
                                    ),
                                containerColor = Danger.copy(alpha = 0.7f),
                            ) {
                                val badgeNumber = searchKeywords.split(",").size.toString()
                                EcodemyText(
                                    format = Nunito.Caption,
                                    data = badgeNumber,
                                    color = Neutral1,
                                    modifier = Modifier
                                        .semantics {
                                            contentDescription = "$badgeNumber new notifications"
                                        }
                                )
                            }
                        }
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.filter),
                        contentDescription = "Filter"
                    )
                }
                Spacer(modifier = Modifier.size(2.dp))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(
        value = "Search",
        onValueChanged = {},
        onSearchTriggered = {},
        onFilterClicked = {},
        searchKeywords = "S,e,a,r,c,h,l,l,l"
    )
}