package com.kltn.ecodemy.domain.models.user

data class UserContact(
    val studentId: String,
    val courseId: String,
    val paymentStatus: String,
    val date: String,
    val enrollContact: EnrollContact,
)

data class EnrollContact(
    val fullName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
)

enum class PaymentStatus(val status: String) {
    NOT_COMPLETED("Not completed"),
    DEPOSIT("Deposit"),
    PAID("Paid");

    companion object {
        private val map = entries.associateBy(PaymentStatus::status)
        fun fromStatus(status: String) = map[status]
    }
}