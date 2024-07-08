package com.kltn.ecodemy.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.kltn.ecodemy.domain.models.chat.Chat
import com.kltn.ecodemy.domain.models.chat.FirebaseClassType
import com.kltn.ecodemy.domain.models.chat.Member
import com.kltn.ecodemy.domain.models.chat.Members
import com.kltn.ecodemy.domain.models.chat.Message

interface FirebaseDataProcess {
    fun updateData(
        messageList: List<Message>,
        chatTitle: String,
        membersList: List<Member>,
        avatarGroup: String,
    )

    fun addNewAccount(
        name: String,
        ownerId: String,
        avatar: String,
    )

    fun removeData()

    fun getMembers(
        ownerId: String,
        chatTitle: String,
        onSuccess: (Members) -> Unit,
    )

    fun getChats(
        ownerId: String,
        onSuccess: (Chat) -> Unit
    )

    fun getJsonToken(
        onSuccess: (Uri) -> Unit
    )

    fun messagesChildrenNodes(
        ownerId: String,
        chatTitle: String,
        onValueChangedListener: (List<Message>) -> Unit,
    )

    fun getAvatarImageUrl(
        chat: Chat,
        onSuccess: (Chat) -> Unit
    )

    fun uploadImage(
        bitmap: Bitmap,
        ownerId: String,
        onSuccess: (Uri) -> Unit,
        onFailure: () -> Unit
    )

    fun uploadFile(
        uri: Uri,
        courseId: String,
        onSuccess: (Uri) -> Unit,
        onFailure: () -> Unit
    )

    fun removeAllListener()
}