package com.kltn.ecodemy.ui.teacherscreens.course

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.clickableWithoutRippleEffect
import com.kltn.ecodemy.domain.models.course.Lesson
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Danger
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary1
import com.kltn.ecodemy.ui.theme.Primary2
import com.kltn.ecodemy.ui.theme.Primary3
import java.io.File

@Composable
fun ResourcesCourseLesson(
    number: String,
    time: String,
    pressed: Boolean,
    sectionIndex: Int,
    lesson: Lesson,
    isUpdated: Boolean = false,
    onAddResources: (
        sectionIndex: Int, lessonIndex: Int,
        file: Uri
    ) -> Unit = { _, _, _ -> },
) {
    val backgroundColor = remember { mutableStateOf(Color.Transparent) }
    val numberContainerColor = remember { mutableStateOf(Primary2) }
    val numberColor = remember { mutableStateOf(Primary3) }
    val titleColor = remember { mutableStateOf(Neutral1) }
    val resourceText = lesson.resource.split("/").last()
    val resourcesList = remember {
        mutableStateOf(emptyList<String>())
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            if (it != null) {
                val path = it.path
                val file = File(path!!)
                onAddResources(sectionIndex, number.toInt().minus(1), it)
                resourcesList.value += it.lastPathSegment.toString()
                Log.d("FileS", Uri.fromFile(file).lastPathSegment.toString())
            }
        }


    LaunchedEffect(key1 = pressed) {
        if (pressed) {
            backgroundColor.value = Primary2
            numberContainerColor.value = Primary3
            numberColor.value = Primary2
            titleColor.value = Primary3
        } else {
            backgroundColor.value = Color.Transparent
            numberContainerColor.value = Primary2
            numberColor.value = Primary3
            titleColor.value = Neutral1
        }
    }
    Row(
        modifier = Modifier
            .background(color = backgroundColor.value, shape = RoundedCornerShape(12.dp))
            .padding(
                top = 8.dp,
                start = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = numberContainerColor.value, shape = RoundedCornerShape(20.dp))
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            EcodemyText(
                format = Nunito.Title1,
                data = number,
                color = numberColor.value
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            EcodemyText(
                format = Nunito.Title1,
                data = lesson.title,
                color = titleColor.value
            )
            Text(
                text = time,
                style = Nunito.Subtitle1.textStyle,
                color = Neutral2
            )
            if (resourceText.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                ResourcesLesson(resourceText = resourceText)
            }

            if (resourcesList.value.isNotEmpty()) {
                resourcesList.value.forEach {
                    Spacer(modifier = Modifier.height(8.dp))
                    ResourcesLesson(resourceText = it)
                }
            }

            if (isUpdated) {
                EcodemyText(
                    format = Nunito.Subtitle3, data = stringResource(R.string.add_resources), color = Neutral3,
                    modifier = Modifier
                        .padding(
                            horizontal = 12.dp,
                            vertical = 8.dp,
                        )
                        .padding(start = 12.dp)
                        .clickableWithoutRippleEffect(true) {
                            launcher.launch(
                                arrayOf(
                                    "application/pdf",
                                    "image/*",
                                )
                            )
                        }
                )
            }
        }
    }
}

@Composable
fun ResourcesLesson(
    modifier: Modifier = Modifier,
    resourceText: String,
) {
    val textDisplay = resourceText.split("?").first().split("%2F").last()
    val textProcess = resourceText.split(".").last()
    val color =
        if (textProcess.split("?").first()
                .lowercase() == "pdf") Danger else Primary1
    val icon = if (color == Primary1) R.drawable.file_image else R.drawable.file_pdf
    Row(
        modifier = Modifier
            .padding(end = 12.dp)
            .then(modifier)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp),
            tint = color
        )

        Spacer(modifier = Modifier.width(4.dp))

        EcodemyText(format = Nunito.Subtitle3, data = textDisplay, color = Neutral1)
    }
}