package com.kltn.ecodemy.data.impl

import android.text.TextUtils
import com.kltn.ecodemy.domain.repository.TextFieldValidation
import java.util.regex.Matcher
import java.util.regex.Pattern

class TextFieldValidationImpl : TextFieldValidation {
    override fun checkMail(mail: String): Boolean =
        !TextUtils.isEmpty(mail) && android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()


    override fun checkFullName(fullName: String): Boolean {
        val pattern: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(fullName)
        return matcher.find()
    }

    override fun checkPassword(password: String): Boolean {
        return if (password.length >= 6) {
            val letter = Pattern.compile("[a-zA-z]")
            val digit = Pattern.compile("[0-9]")
            val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
            val hasLetter = letter.matcher(password)
            val hasDigit = digit.matcher(password)
            val hasSpecial = special.matcher(password)
            hasLetter.find() && hasDigit.find() && hasSpecial.find()
        } else false
    }

    override fun checkPhone(phone: String): Boolean {
        return if (phone.length >= 10) {
            val digit = Pattern.compile("[0-9]")
            val special = Pattern.compile("[!@#$%&*()_=|<>?{}\\[\\]~]")
            val hasDigit = digit.matcher(phone)
            val hasSpecial = special.matcher(phone)
            hasDigit.find() && !hasSpecial.find()
        } else false
    }

    override fun checkReEnterPassword(password: String, reEnterPassword: String): Boolean =
        password == reEnterPassword

}