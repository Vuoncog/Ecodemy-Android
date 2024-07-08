package com.kltn.ecodemy.ui.components

import androidx.compose.runtime.Composable

@Composable
fun SelectionButton(
    textContent: String,
    selected: String,
    onUnselectedButtonClicked: () -> Unit,
) {
    if (textContent == selected) {
        KocoButton(
            textContent = textContent,
            icon = null,
            onClick = {},
        )
    } else KocoTextButton(
        textContent = textContent,
        icon = null,
        onClick = onUnselectedButtonClicked,
    )
}