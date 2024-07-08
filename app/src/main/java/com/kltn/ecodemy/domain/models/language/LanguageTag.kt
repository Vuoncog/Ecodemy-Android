package com.kltn.ecodemy.domain.models.language

import androidx.annotation.DrawableRes
import com.kltn.ecodemy.R
import com.kltn.ecodemy.domain.models.user.Role

enum class LanguageTag(
    val tag: String,
    val langName: String,
    @DrawableRes val systemLangName: Int,
) {
    VIETNAMESE("vi", "Tiếng Việt", R.string.vietnamese),
    ENGLISH("en", "English", R.string.english);

    companion object {
        fun of(tag: String): LanguageTag? = LanguageTag.entries.find { it.tag == tag }
    }
}