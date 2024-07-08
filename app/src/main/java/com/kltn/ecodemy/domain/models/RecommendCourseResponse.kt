package com.kltn.ecodemy.domain.models

data class RecommendCourseResponse(
    val _id: String,
    val ownerId: String,
    val courseRecommendList: List<String>,
    val lastestCourse: String,
)