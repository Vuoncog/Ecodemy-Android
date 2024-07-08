package com.kltn.ecodemy.data.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.OWNER_ID
import com.kltn.ecodemy.constant.Constant.SPLITTER
import com.kltn.ecodemy.constant.Constant.START_DESTINATION
import com.kltn.ecodemy.data.navigation.Route.Arg.CATEGORY_KEYWORD
import com.kltn.ecodemy.data.navigation.Route.Arg.CATEGORY_TYPE
import com.kltn.ecodemy.data.navigation.Route.Arg.COURSE_ID
import com.kltn.ecodemy.data.navigation.Route.Arg.LESSON_ID
import com.kltn.ecodemy.data.navigation.Route.Arg.RESOURCE_PDF_URL
import com.kltn.ecodemy.data.navigation.Route.Arg.SEARCH_TEXT
import com.kltn.ecodemy.data.navigation.Route.Arg.SECTION_ID
import com.kltn.ecodemy.data.navigation.Route.Arg.STUDENT_ID

sealed class Route(
    val title: Int,
    val route: String,
    @DrawableRes val icon: Int = R.drawable.bell,
) {
    object Arg {
        const val SECTION_ID = "sectionId"
        const val LESSON_ID = "lessonId"
        const val COURSE_ID = "courseId"
        const val SEARCH_TEXT = "searchText"
        const val RESOURCE_PDF_URL = "pdfUrl"
        const val CATEGORY_TYPE = "categoryType"
        const val CATEGORY_KEYWORD = "CATEGORY_KEYWORD"
        const val STUDENT_ID = "studentId"
    }

    data object Home : Route(
        title = R.string.route_home,
        route = "home",
        icon = R.drawable.home_alt_2,
    )

    data object Search : Route(
        title = R.string.route_search,
        route = "search",
        icon = R.drawable.search,
    ) {
        fun resultRoute() = "${this.route}/{$SEARCH_TEXT}"
        fun resultArgs(
            searchText: String
        ) = "${this.route}/$searchText"
    }

    data object Learn : Route(
        title = R.string.route_learn,
        route = "learn",
        icon = R.drawable.book,
    )

    data object Wishlist : Route(
        title = R.string.route_wishlist,
        route = "wishlist",
        icon = R.drawable.heart,
    )

    data object Account : Route(
        title = R.string.route_account,
        route = "account",
        icon = R.drawable.user,
    )

    data object Lesson : Route(
        title = R.string.lesson,
        route = "lesson",
        icon = R.drawable.home_alt_2,
    ) {
        private fun lessonId(
            courseId: String,
            sectionId: Int,
            lessonId: Int,
        ) = "${this.route}/$courseId/$sectionId/$lessonId"

        fun navigateLesson(
            navController: NavHostController,
            courseId: String,
            sectionId: Int,
            lessonId: Int,
        ) {
            navController.navigate(
                lessonId(
                    courseId = courseId,
                    sectionId = sectionId,
                    lessonId = lessonId,
                )
            )
        }

        fun lessonRoute() =
            "${this.route}/{$COURSE_ID}/{$SECTION_ID}/{$LESSON_ID}"

        fun resourcesRoute() = "${this.route}?$RESOURCE_PDF_URL={${RESOURCE_PDF_URL}}"
        fun resourcesArg(pdfUrl: String) = "${this.route}?$RESOURCE_PDF_URL=$pdfUrl"
    }

    data object Course : Route(
        title = R.string.route_course,
        route = "course",
        icon = R.drawable.home_alt_2,
    ) {

        fun lessonIdArguments(list: List<String>): String {
            val args = mutableStateOf("args")
            list.forEach {
                args.value += "$SPLITTER$it"
            }
            return args.value.drop(SPLITTER.length + 4)
        }
    }

    data object Notification : Route(
        title = R.string.notification,
        route = "notification",
        icon = R.drawable.home_alt_2,
    )

    data object Setting : Route(
        title = R.string.route_setting,
        route = "setting",
        icon = R.drawable.home_alt_2,
    ) {
        sealed class SettingItem(
            @DrawableRes val title: Int,
            val settingRoute: String,
            val args: String? = null
        ) {
            data object Language :
                SettingItem(title = R.string.language, settingRoute = "settings/language")

            data object ChangePassword : SettingItem(
                title = R.string.change_password,
                settingRoute = "settings/change-password"
            )

            data class PaymentHistory(val arg: String = "") : SettingItem(
                title = R.string.payment_history,
                settingRoute = "settings/payment-history",
                args = arg
            )

            data object ChangeAvatar :
                SettingItem(
                    title = R.string.change_avatar,
                    settingRoute = "settings/change-avatar",
                )

            data object CapturePhoto :
                SettingItem(
                    title = R.string.capture_the_photo,
                    settingRoute = "settings/capture-photo"
                )
        }
    }

    data object Teacher : Route(
        title = R.string.route_teacher,
        route = "teacher"
    )

    data object Chat : Route(
        title = R.string.chat,
        route = "chat"
    ) {
        const val CHAT_LIST = "chatList"
        const val CHAT_TITLE = "chatTitle"
        const val IMAGE_URL = "imageUrl"
        const val MEMBERS = "members"

        fun messagesRoute() = "${this.route}/{$CHAT_TITLE}/{$IMAGE_URL}"
        fun memberRoute() = "${this.route}/$MEMBERS"
        fun chatListRoute() = this.route + "/$CHAT_LIST"
        fun messagesArgument(chatTitle: String, imageUrl: String) =
            "${this.route}/$chatTitle/$imageUrl"
    }

    data object Authentication : Route(
        title = R.string.route_authentication,
        route = "authentication"
    ) {
        const val LOGIN = "login"
        const val SIGNUP = "signup"
        const val OTP = "otp"
        const val SUCCESS = "otp_success/{otp}"
        const val SIGN_UP_SUCCESS = "otp_success/true"
        const val FORGET_SUCCESS = "otp_success/false"
        const val FORGET = "forget_password"
        const val OTP_FORGET = "otp_forget"
        const val CHANGE_NEW_PASSWORD = "change_new_password"
    }

    data object Payment : Route(
        title = R.string.route_payment,
        route = "Payment"
    ) {
        fun paymentRoute() = "${this.route}/{$COURSE_ID}"
        fun paymentArgs(
            courseId: String
        ) = "${this.route}/$courseId"
    }

    data object RegisterForm : Route(
        title = R.string.route_register,
        route = "registerForm"
    ) {
        fun registerFormRoute() = "${this.route}/{$COURSE_ID}"
        fun registerFormArgs(
            courseId: String
        ) = "${this.route}/$courseId"
    }

    data object Review : Route(
        title = R.string.review,
        route = "review"
    ) {
        fun reviewRoute() = "${this.route}/{$COURSE_ID}/{$STUDENT_ID}"
        fun reviewArgs(courseId: String, studentId: String) = "${this.route}/$courseId/$studentId"
    }

    data object Category : Route(
        title = R.string.category_title,
        route = "category",
    ) {
        fun categoryRoute() = "${this.route}/{$CATEGORY_TYPE}/{$CATEGORY_KEYWORD}"
        fun categoryArgs(type: String, keyword: String) = "${this.route}/$type/$keyword"
    }
}