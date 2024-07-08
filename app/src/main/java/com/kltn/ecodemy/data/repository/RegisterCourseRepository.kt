package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.domain.models.user.UserContact
import com.kltn.ecodemy.domain.repository.RegisterCourseDataProcess
import javax.inject.Inject

class RegisterCourseRepository @Inject constructor(
    private val registerCourseDataProcess: RegisterCourseDataProcess
) {
    suspend fun registeredCourseUser(
        userContact: UserContact,
        onSuccess: () -> Unit
    ) = registerCourseDataProcess.registeredCourseUser(userContact, onSuccess)

}