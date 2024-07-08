package com.kltn.ecodemy.domain.models.course

data class About(
    val allCourse: MainAbout = MainAbout(),
    val offCourse: OfflineCourseAbout = OfflineCourseAbout(),
)

data class MainAbout(
    val about: String ="",
    val prerequisite: String = "",
    val output: String = "",
    val lecture: String = "",
)

data class OfflineCourseAbout(
    val classDate: String = "",
    val classSize: String = "",
    val schedule: String = "",
)