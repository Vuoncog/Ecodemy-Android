package com.kltn.ecodemy.domain.models.chat

data class Chat(
    var chatTitle: String? = "",
    var latestMessage: LatestMessage? = LatestMessage()
){
    fun toMessage(): Message = Message(
        member = latestMessage?.member,
        timestamp = latestMessage?.timestamp,
        message = latestMessage?.message
    )
}
