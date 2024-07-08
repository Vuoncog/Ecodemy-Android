package com.kltn.ecodemy.domain.models

interface PostResponse {
    val insertedId: String
}

data class UserPostResponse(
    override val insertedId: String = ""
) : PostResponse

data class UpdateUserDataResponse(
    val matchedCount: Int,
    val modifiedCount: Int
)

data class PaymentDetailResponse(
    override val insertedId: String = ""
) : PostResponse

data class EnrolledUserResponse(
    override val insertedId: String = ""
) : PostResponse