package com.kltn.ecodemy.domain.repository

import com.kltn.ecodemy.domain.models.user.UserContact

interface RegisterCourseDataProcess {
    suspend fun registeredCourseUser(
        userContact: UserContact,
        onSuccess: () -> Unit
    )
}