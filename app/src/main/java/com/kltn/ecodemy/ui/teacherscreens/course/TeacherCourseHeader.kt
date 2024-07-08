package com.kltn.ecodemy.ui.teacherscreens.course

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.StartLinear

@Composable
fun TeacherCourseHeader(
    onBackClicked: () -> Unit,
    showTrailingIcon: Boolean,
    onTrailingIconClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(StartLinear)
            .height(56.dp)
            .padding(horizontal = Constant.PADDING_SCREEN),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                contentDescription = "Back"
            )
        }
        EcodemyText(
            format = Nunito.Heading2,
            data = stringResource(R.string.course_information),
            color = Neutral1,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        if (showTrailingIcon) {
            IconButton(onClick = onTrailingIconClicked) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.x),
                    contentDescription = "Back"
                )
            }
        } else {
            Spacer(modifier = Modifier.size(Constant.ICON_INTERACTIVE_SIZE))
        }
    }
}