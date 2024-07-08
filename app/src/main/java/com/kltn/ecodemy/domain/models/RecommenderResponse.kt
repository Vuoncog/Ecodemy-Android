package com.kltn.ecodemy.domain.models

data class RecommenderResponse(
    val itemsets: List<String>,
    val support: Double
)
