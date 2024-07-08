package com.kltn.ecodemy.domain.repository

interface AccessToken {
    fun setAccessToken()
    fun getAccessToken(): String
}