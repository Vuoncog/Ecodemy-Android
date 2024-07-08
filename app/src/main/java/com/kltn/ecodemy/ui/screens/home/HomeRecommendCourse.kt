package com.kltn.ecodemy.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.CourseCard
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito
import com.kltn.ecodemy.ui.theme.Primary3

private val COURSE_SPACE_BETWEEN = Arrangement.spacedBy(32.dp)
private val COURSE_RECOMMENDATION_VERTICAL_PADDING = PaddingValues(top = 12.dp, bottom = 8.dp)

@Composable
fun HomeRecommendCourse(
    onCardClicked: () -> Unit,
) {
    val image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg"

    Column(
        verticalArrangement = Constant.ITEM_SPACE,
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = Constant.PADDING_SCREEN)
            .padding(COURSE_RECOMMENDATION_VERTICAL_PADDING),
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Neutral1,
                        fontStyle = Nunito.Heading2.textStyle.fontStyle,
                        fontWeight = Nunito.Heading2.textStyle.fontWeight,
                        fontSize = Nunito.Heading2.textStyle.fontSize
                    )
                ) {
                    append(stringResource(id = R.string.home_recommend_course) + " ")
                }

                withStyle(
                    style = SpanStyle(
                        color = Primary3,
                        fontStyle = Nunito.Heading2.textStyle.fontStyle,
                        fontWeight = Nunito.Heading2.textStyle.fontWeight,
                        fontSize = Nunito.Heading2.textStyle.fontSize
                    )
                ) {
                    append("iOS")
                }
            },
        )
//        LazyRow(content = )
        Row(
            horizontalArrangement = COURSE_SPACE_BETWEEN,
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeRecommendPrev() {
    HomeRecommendCourse({})
}
