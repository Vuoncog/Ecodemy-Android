package com.kltn.ecodemy.domain.models.course

data class UserRecommendationCourse(
    val courses: List<Course> = emptyList(),
    val latestCourse: String,
    val category: List<String> = emptyList()
)
