package com.kltn.ecodemy.ui.screens.course.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.topBorder
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun CourseBottomBar(
    modifier: Modifier = Modifier,
    price: Double,
    isLogout: Boolean,
    onEnrollClicked: () -> Unit,
    onChatToConsultant: () -> Unit,
    courseType: Boolean,
    isPurchased: Boolean,
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .topBorder(
                strokeWidth = 1.dp,
                color = Neutral3
            )
            .background(Color.White)
            .padding(vertical = 8.dp)
            .padding(
                start = 16.dp,
                end = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.course_price),
                style = Nunito.Subtitle1.textStyle,
                color = Neutral3
            )
            Text(
                text = "$ $price",
                style = Nunito.Heading2.textStyle,
                color = Neutral1
            )
        }
        if (!courseType && !isLogout) {
            Icon(imageVector = ImageVector.vectorResource(R.drawable.message_rounded_dots),
                contentDescription = "Chat",
                modifier = Modifier.clickable {
                    onChatToConsultant()
                }
            )
        }
        if (!isLogout){
            KocoButton(
                disable = isPurchased,
                textContent = if (isPurchased) stringResource(R.string.owned)
                else if (courseType) stringResource(
                    id = R.string.purchase_title
                ) else stringResource(R.string.register),
                icon = null,
                onClick = onEnrollClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseBottomBarPrev() {
    CourseBottomBar(
        price = 19.999,
        onEnrollClicked = {},
        onChatToConsultant = {},
        isPurchased = false,
        isLogout = true,
        courseType = false
    )
}