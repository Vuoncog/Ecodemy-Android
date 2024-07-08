package com.kltn.ecodemy.domain.repository

interface TextFieldValidation {

    fun checkMail(mail: String): Boolean
    fun checkFullName(fullName: String): Boolean
    fun checkPassword(password: String): Boolean
    fun checkPhone(phone: String): Boolean
    fun checkReEnterPassword(password: String, reEnterPassword: String): Boolean

}