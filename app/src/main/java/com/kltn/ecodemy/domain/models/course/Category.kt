package com.kltn.ecodemy.domain.models.course

import androidx.annotation.DrawableRes
import com.kltn.ecodemy.R

enum class Category(@DrawableRes val icon: Int,) {
    Mobile(icon = R.drawable.mobile_alt), Web(icon = R.drawable.devices),
    AI(icon = R.drawable.graphql), Analysis(icon = R.drawable.analyse),
}