package com.kltn.ecodemy.data.navigation

import com.kltn.ecodemy.constant.Constant

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val MAIN = "main_graph"
    const val HOME = "home_graph"
    const val SEARCH = "search_graph"
    const val LEARNING = "learning_graph"
    const val WISHLIST = "wishlist_graph"
    const val ACCOUNT = "account_graph"
    const val COURSE = "course_graph"
    const val SETTING = "setting_graph"
    const val TEACHER = "teacher_graph"
    const val CHAT = "chat_graph"
    const val TEACHER_MAIN = "teacher_main_graph"
}

fun String.argument(type: String) = "$this?${type}={${type}}"
fun String.addArgument(arg: String?, type: String): String{
    return if (arg != null) {
        "$this?${type}=$arg"
    } else {
        this
    }
}