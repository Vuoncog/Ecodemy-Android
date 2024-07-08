package com.kltn.ecodemy.ui.screens.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.StartLinear

private val TEACHER_TOP_BAR_HEIGHT = 56.dp

@Composable
fun TeacherTopBar(
    onBackClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(TEACHER_TOP_BAR_HEIGHT)
            .background(StartLinear)
            .padding(
                end = 4.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                contentDescription = "Back",
                tint = Neutral1
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.route_teacher),
                style = Nunito.Heading2.textStyle,
                color = Neutral1,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.size(Constant.ICON_INTERACTIVE_SIZE))
    }
}