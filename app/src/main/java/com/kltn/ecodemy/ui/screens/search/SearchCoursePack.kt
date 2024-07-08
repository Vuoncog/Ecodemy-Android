package com.kltn.ecodemy.ui.screens.search

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.components.KocoButton
import com.kltn.ecodemy.ui.components.EcodemyText
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Neutral2
import com.kltn.ecodemy.ui.theme.Nunito

private val SEARCH_COURSE_PACK_VERTICAL_PADDING = PaddingValues(vertical = 12.dp)
private val SEARCH_COURSE_PACK_TEXT_TOP_PADDING = PaddingValues(top = 4.dp, bottom = 8.dp)

@Composable
fun SearchCoursePack(
    context: Context = LocalContext.current,
    coursePackTitle: String,
    coursePackSlogan: String,
    onDetailClicked: () -> Unit,
) {
    val imageRequest = ImageRequest.Builder(context = context)
        .data(R.drawable.combo_course)
        .placeholder(R.drawable.combo_course)
        .error(R.drawable.combo_course)
        .crossfade(true)
        .allowHardware(false)
        .build()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val coursePackSize = Size(
        screenWidth,
        screenWidth / 16 * 9
    )

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(SEARCH_COURSE_PACK_VERTICAL_PADDING)
    ) {
        EcodemyText(
            format = Nunito.Heading2,
            data = "Course pack",
            color = Neutral1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Constant.PADDING_SCREEN)
        )
        Spacer(modifier = Modifier.size(8.dp))

        AsyncImage(
            modifier = Modifier
                .size(
                    width = (coursePackSize.width).dp,
                    height = (coursePackSize.height).dp
                ),
            model = imageRequest,
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Column(
            modifier = Modifier
                .padding(SEARCH_COURSE_PACK_TEXT_TOP_PADDING)
                .padding(horizontal = Constant.PADDING_SCREEN)
        ) {
            EcodemyText(
                format = Nunito.Heading2,
                data = coursePackTitle,
                color = Neutral1,
                modifier = Modifier
                    .fillMaxWidth()
            )
            EcodemyText(
                format = Nunito.Subtitle1,
                data = coursePackSlogan,
                color = Neutral2,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(8.dp))
            KocoButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDetailClicked,
                textContent = "See course pack detail",
                icon = null,
            )
        }
    }
}

@Preview
@Composable
fun SearchCoursePackPrev() {
    SearchCoursePack(
        coursePackTitle = "Course pack for Graphic designer",
        coursePackSlogan = "Master Swift UI, become a iOS developer"
    ) {

    }
}