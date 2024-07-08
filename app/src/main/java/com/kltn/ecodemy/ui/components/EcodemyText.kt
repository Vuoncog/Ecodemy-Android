package com.kltn.ecodemy.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito

@Composable
fun EcodemyAnnotatedText(
    modifier: Modifier = Modifier,
    format: Nunito,
    data: AnnotatedString,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = Modifier.then(modifier),
        text = data,
        style = format.textStyle,
        textAlign = textAlign,
        lineHeight = format.textStyle.lineHeight
    )
}

@Composable
fun EcodemyText(
    modifier: Modifier = Modifier,
    format: Nunito,
    data: String,
    color: Color,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = Modifier.then(modifier),
        text = data,
        style = format.textStyle,
        color = color,
        textAlign = textAlign,
        lineHeight = format.textStyle.lineHeight,
        maxLines = maxLines,
        overflow = TextOverflow.Clip,
        textDecoration = textDecoration
    )
}

@Preview
@Composable
fun KocoTextPrev() {
    Box(modifier = Modifier.width(248.dp)) {
        EcodemyText(
            format = Nunito.Title1,
            data = "Build Kotlin Multiplatform Build Kotlin Multiplatform",
            color = Neutral1
        )
    }
}