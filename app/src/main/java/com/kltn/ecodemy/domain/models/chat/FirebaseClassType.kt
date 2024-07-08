package com.kltn.ecodemy.domain.models.chat

enum class FirebaseClassType(
    val childNode: String,
    val classType: Class<*>,
) {
    CHAT(
        childNode = "chats",
        classType = Message::class.java,
    ),
    MEMBER(
        childNode = "members",
        classType = Members::class.java,
    ),
    MESSAGE(
        childNode = "messages",
        classType = List::class.java,
    )
}