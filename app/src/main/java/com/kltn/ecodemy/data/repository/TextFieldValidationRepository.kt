package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.domain.repository.TextFieldValidation
import javax.inject.Inject

class TextFieldValidationRepository @Inject constructor(
    private val textFieldValidation: TextFieldValidation
) {
    fun checkMail(email: String): Boolean = textFieldValidation.checkMail(email)
    fun checkFullName(fullName: String) =
        (!textFieldValidation.checkFullName(fullName)).and(fullName.isNotEmpty())

    fun checkPassword(password: String) = textFieldValidation.checkPassword(password)
    fun checkPhone(phone: String) = textFieldValidation.checkPhone(phone)
    fun checkReEnterPassword(password: String, reEnterPassword: String) =
        textFieldValidation.checkReEnterPassword(password, reEnterPassword)
}