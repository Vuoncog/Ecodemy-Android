package com.kltn.ecodemy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kltn.ecodemy.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

sealed class Nunito(val textStyle: TextStyle) {
    data object Title1 : Nunito(
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = NunitoFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            letterSpacing = (0.4).sp,
            lineHeight = 22.sp,
            color = Neutral1
        )
    )

    data object Title2 : Nunito(
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = NunitoFamily,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (0.4).sp,
            lineHeight = 22.sp,
            color = Neutral1
        )
    )

    data object Body : Nunito(
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = NunitoFamily,
            fontWeight = FontWeight.Medium,
            letterSpacing = (0.1).sp,
            lineHeight = 19.sp,
            color = Neutral1
        )
    )

    data object Subtitle1 : Nunito(
        textStyle = TextStyle(
            fontFamily = NunitoFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.25.sp,
            lineHeight = 19.sp,
            color = Neutral1
        )
    )

    data object Subtitle2 : Nunito(
        textStyle = TextStyle(
            fontFamily = NunitoFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.25.sp,
            lineHeight = 16.sp
        )
    )

    data object Subtitle3 : Nunito(
        textStyle = TextStyle(
            fontFamily = NunitoFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.25.sp,
            lineHeight = 19.sp,
            color = Neutral1
        )
    )

    data object Heading2 : Nunito(
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = NunitoFamily,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.4.sp,
            lineHeight = 27.sp,
            color = Neutral1
        )
    )

    data object Heading1 : Nunito(
        textStyle = TextStyle(
            fontSize = 24.sp,
            fontFamily = NunitoFamily,
            fontWeight = FontWeight.Normal,
            lineHeight = 33.sp,
            letterSpacing = 0.sp,
            color = Neutral1
        )
    )

    data object Caption : Nunito(
        textStyle = TextStyle(
            fontSize = 10.sp,
            fontFamily = NunitoFamily,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 12.sp,
            letterSpacing = 0.25.sp,
            color = Neutral1
        )
    )
}

val NunitoFamily = FontFamily(
    Font(R.font.nunito_black, weight = FontWeight.Black),
    Font(R.font.nunito_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.nunito_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.nunito_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.nunito_extrabold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
    Font(R.font.nunito_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.nunito_extralight, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
    Font(R.font.nunito_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.nunito_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.nunito_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(R.font.nunito_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.nunito_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.nunito_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.nunito_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.nunito_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(R.font.nunito_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
)