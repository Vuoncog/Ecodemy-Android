package com.kltn.ecodemy.domain.models.chat

data class Members(
    var chatTitle: String? = "",
    var members: List<Member>? = listOf(),
)

data class Member(
    var name: String? = "",
    var ownerId: String? = "",
    var avatar: String? = "",
    var token: String? = null
)
