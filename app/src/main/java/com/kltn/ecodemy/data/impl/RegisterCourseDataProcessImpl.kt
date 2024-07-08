package com.kltn.ecodemy.data.impl

import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.models.user.UserContact
import com.kltn.ecodemy.domain.repository.RegisterCourseDataProcess
import javax.inject.Inject

class RegisterCourseDataProcessImpl @Inject constructor(
    private val ecodemyApi: EcodemyApi
) : RegisterCourseDataProcess {

    override suspend fun registeredCourseUser(
        userContact: UserContact,
        onSuccess: () -> Unit
    ) {
        val response = ecodemyApi.insertEnrolledCourseUser(userContact)
        if (response.insertedId.isNotBlank()){
            onSuccess()
        }
    }
}