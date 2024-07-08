package com.kltn.ecodemy.domain.models.chat

data class Messages(
    var chatTitle: String? = "",
    var messages: List<Message>? = listOf()
)

data class LatestMessage(
    var avatar: String? = "",
    var member: String? = "",
    var timestamp: Long? = 0,
    var message: String? = "",
    var ownerId: String? = ""
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "member" to member,
            "timestamp" to timestamp,
            "message" to message,
            "ownerId" to ownerId,
            "avatar" to avatar
        )
    }
}

data class Message(
    var member: String? = "",
    var timestamp: Long? = 0,
    var message: String? = "",
    var ownerId: String? = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "member" to member,
            "timestamp" to timestamp,
            "message" to message,
            "ownerId" to ownerId
        )
    }

    fun toLastMessage(avatarGroup: String) = LatestMessage(
        avatar = avatarGroup,
        member = member,
        timestamp = timestamp,
        message = message,
        ownerId = ownerId
    )

    companion object {
        fun from(map: Map<String, Any>) = object {
            val member by map
            val timestamp by map
            val message by map

            val data = Message(member.toString(), timestamp.toString().toLong(), message.toString())
        }.data
    }
}
