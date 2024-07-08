package com.kltn.ecodemy.domain.models.user

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.kltn.ecodemy.ui.theme.Neutral3
import com.kltn.ecodemy.ui.theme.Other1Darker
import com.kltn.ecodemy.ui.theme.Primary3

enum class Role(
    val color: Color
) {
    @SerializedName("Guide")
    Guide(color = Neutral3),

    @SerializedName("Student")
    Student(color = Primary3),

    @SerializedName("Teacher")
    Teacher(color = Other1Darker);

    companion object {
        fun of(role: String): Role? = entries.find { it.name == role }
    }
}