package com.kltn.ecodemy.ui.screens.search

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.user.User
import com.kltn.ecodemy.domain.models.user.UserInfo
import com.kltn.ecodemy.ui.components.TeacherItem
import com.kltn.ecodemy.ui.theme.Neutral1
import com.kltn.ecodemy.ui.theme.Nunito

private val TEACHER_VERTICAL_PADDING = PaddingValues(top = 12.dp, bottom = 8.dp)

@Composable
fun SearchTeacher(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    teachers: List<User>,
    onTeacherItemClicked: (String) -> Unit
) {
    Column(
        verticalArrangement = Constant.ITEM_SPACE,
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = Constant.PADDING_SCREEN)
            .padding(TEACHER_VERTICAL_PADDING)
            .then(modifier),
    ) {
        Text(
            text = stringResource(id = R.string.teachers_title),
            style = Nunito.Heading2.textStyle,
            color = Neutral1
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(teachers) { teacher ->
                TeacherItem(
                    context = context,
                    teacherId = teacher._id!!,
                    teacher = teacher.userInfo,
                    onClick = onTeacherItemClicked
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeacherPrev() {
    val teachers = listOf(
        User(
            _id = "65dff39bbc8c44f59386ace7",
            userInfo = UserInfo(
                fullName = "Teacher 2"
            )
        ),
        User(
            _id = "65dff4b6bc8c44f59386acef",
            userInfo = UserInfo(
                fullName = "Teacher 1"
            )
        ),
    )
    SearchTeacher(
        teachers = teachers,
        onTeacherItemClicked = {},
    )
}