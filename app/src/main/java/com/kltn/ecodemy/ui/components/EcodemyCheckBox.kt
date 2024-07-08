package com.kltn.ecodemy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.Primary2

private val CHECKBOX_SHAPE = RoundedCornerShape(6.dp)

@Composable
fun EcodemyCheckBox(
    status: Boolean = false,
    onChecked: (String) -> Unit,
    label: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (status) {
            CheckedBox {
                onChecked(label)
            }
        } else {
            UncheckBox {
                onChecked("")
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        EcodemyText(format = Nunito.Title2, data = label, color = Neutral1)
    }
}

@Composable
private fun UncheckBox(
    onClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CHECKBOX_SHAPE)
            .clickable { onClicked() }
            .border(
                width = 1.5.dp,
                color = Neutral2,
                shape = CHECKBOX_SHAPE
            )
    )
}

@Composable
private fun CheckedBox(
    onClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CHECKBOX_SHAPE)
            .clickable { onClicked() }
            .background(Primary2)
            .border(
                width = 1.dp,
                color = Primary1,
                shape = CHECKBOX_SHAPE
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.check),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Primary1
        )
    }
}