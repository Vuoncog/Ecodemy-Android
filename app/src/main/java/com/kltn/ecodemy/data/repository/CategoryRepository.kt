package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.data.api.EcodemyApi
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val ecodemyApi: EcodemyApi
) {

    fun getCoursesWithKeyword(keyword: String) {

    }
}