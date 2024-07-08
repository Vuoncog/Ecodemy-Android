package com.kltn.ecodemy.data.impl

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.domain.models.chat.Chat
import com.kltn.ecodemy.domain.models.chat.LatestMessage
import com.kltn.ecodemy.domain.models.chat.Member
import com.kltn.ecodemy.domain.models.chat.Members
import com.kltn.ecodemy.domain.models.chat.Message
import com.kltn.ecodemy.domain.models.chat.Messages
import com.kltn.ecodemy.domain.repository.FirebaseDataProcess
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.ZoneOffset


class FirebaseDataProcessImpl : FirebaseDataProcess {
    private val database: DatabaseReference =
        Firebase.database(Constant.FIREBASE_DATABASE_URL).reference
    private val storage: StorageReference =
        FirebaseStorage.getInstance().reference

    private val userDatabaseRef = database.child("users")
    private val userStorageRef = storage.child("users")

    private val chats = "chats"
    private val messages = "messages"
    private val members = "members"
    private val images = "images"
    private val ecodemy = "Ecodemy"
    private val ecodemyAvatar =
        "https://firebasestorage.googleapis.com/v0/b/ecodemy-c30c8.appspot.com/o/images%2Fecodemy.png?alt=media&token=80d1bdd3-a4f7-4abc-8c32-53820286e32c"
    private val avatar = "avatar.jpg"
    private lateinit var messagesChildEventListener: ChildEventListener

    @RequiresApi(Build.VERSION_CODES.O)
    override fun addNewAccount(
        name: String,
        ownerId: String,
        avatar: String,
    ) {
        Log.d("FirebaseAdd", ownerId)
        val latestMessage = Message(
            member = "Ecodemy",
            timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            message = "Welcome to Ecodemy app"
        )
        val newNode = userDatabaseRef.child(ownerId)
        val map = hashMapOf(
            "$chats/$ecodemy" to Chat(
                latestMessage = LatestMessage(
                    avatar = ecodemyAvatar,
                    member = latestMessage.member,
                    timestamp = latestMessage.timestamp,
                    message = latestMessage.message
                )
            ).latestMessage,
            "$messages/$ecodemy" to Messages(
                messages = listOf(latestMessage)
            ).messages,
            "$members/$ecodemy" to Members(
                members = listOf(
                    Member(
                        name = ecodemy,
                        ownerId = ecodemy,
                        avatar = ecodemyAvatar
                    ),
                    Member(
                        name = name,
                        ownerId = ownerId,
                        avatar = avatar
                    )
                )
            ).members
        )
        map.forEach { update ->
            val hashmap = mapOf(
                update.key to update.value
            )
            newNode.updateChildren(hashmap).addOnFailureListener {
                Log.d("FirebaseAdd", it.toString())
            }.addOnSuccessListener {
                Log.d("FirebaseAdd", "Add Success")
            }
        }
    }

    override fun messagesChildrenNodes(
        ownerId: String,
        chatTitle: String,
        onValueChangedListener: (List<Message>) -> Unit,
    ) {
        messagesChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.key == chatTitle) {
                    val messagesList = snapshot.getValue<List<Message>>()
                    onValueChangedListener(messagesList ?: emptyList())
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.key == chatTitle) {
                    val messagesList = snapshot.getValue<List<Message>>()
                    onValueChangedListener(messagesList ?: emptyList())
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        userDatabaseRef.child(ownerId).child(messages)
            .addChildEventListener(messagesChildEventListener)
    }

    override fun getAvatarImageUrl(
        chat: Chat,
        onSuccess: (Chat) -> Unit
    ) {
        val avatarPath = chat.latestMessage?.avatar
        storage.child("$images/$avatarPath").downloadUrl.addOnSuccessListener { uri ->
            val latestMessage = chat.latestMessage
            val newChat = chat.copy(
                latestMessage = latestMessage?.copy(
                    avatar = uri.toString()
                )
            )
            onSuccess(newChat)
        }.addOnFailureListener { e ->
            onSuccess(chat)
        }
    }

    override fun getJsonToken(
        onSuccess: (Uri) -> Unit
    ){
        storage.child("ecodemy-c30c8-firebase-adminsdk-95ces-ae5f1e96b5.json").downloadUrl.addOnSuccessListener {
            uri -> onSuccess(uri)
        }
    }


    override fun getChats(ownerId: String, onSuccess: (Chat) -> Unit) {
        userDatabaseRef.child(ownerId).child(chats)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val titleId = it.key
                        val latestMessage = it.getValue<LatestMessage>()
                        onSuccess(
                            Chat(
                                chatTitle = titleId,
                                latestMessage = latestMessage
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
            )
    }

    override fun getMembers(ownerId: String, chatTitle: String, onSuccess: (Members) -> Unit) {
        userDatabaseRef.child(ownerId).child(members).child(chatTitle).get()
            .addOnSuccessListener { dataSnapshot ->
                val members = dataSnapshot.getValue<List<Member>>()
                onSuccess(
                    Members(
                        chatTitle = chatTitle,
                        members = members
                    )
                )
            }
    }

    override fun updateData(
        messageList: List<Message>,
        chatTitle: String,
        membersList: List<Member>,
        avatarGroup: String,
    ) {
        val messageValue = messageList.mapIndexed { index, mess -> index.toString() to mess }
            .toMap()

        val membersValue = membersList.mapIndexed { index, mess -> index.toString() to mess }
            .toMap()

        val childUpdates = chatTitle.updateHashMap(
            messageValue = messageValue,
            latestMessage = messageList.last().toLastMessage(avatarGroup).toMap(),
            membersValue = membersValue
        )

        Log.d("Last Message", messageList.last().toMap().toString())
        membersList.forEach {
            val toConsultant = membersList.contains(
                Member(
                    name = ecodemy,
                    ownerId = ecodemy,
                    avatar = ecodemyAvatar
                )
            )
            if (it.ownerId != ecodemy) {
                //Update for user
                childUpdates.forEach { update ->
                    val hashmap = mapOf(
                        update.key to update.value
                    )
                    userDatabaseRef.child(it.ownerId!!).updateChildren(hashmap)
                }

                if (toConsultant) {
                    //Update for consultant with chatTitle = other's ownerId
                    val consultantChildUpdates = it.ownerId!!.updateHashMap(
                        messageValue = messageValue,
                        latestMessage = messageList.last().toLastMessage(avatarGroup).toMap(),
                        membersValue = membersValue
                    )

                    consultantChildUpdates.forEach { update ->
                        val hashmap = mapOf(
                            update.key to update.value
                        )
                        userDatabaseRef.child(ecodemy).updateChildren(hashmap)
                    }
                }
            }
        }
    }

    override fun uploadImage(
        bitmap: Bitmap,
        ownerId: String,
        onSuccess: (Uri) -> Unit,
        onFailure: () -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val avatarFile = baos.toByteArray()

        val userRef = userStorageRef.child(ownerId).child(avatar)
        val uploadTask = userRef.putBytes(avatarFile)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            userRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                onSuccess(downloadUri)
            } else {
                onFailure()
            }
        }
    }

    override fun uploadFile(
        uri: Uri,
        courseId: String,
        onSuccess: (Uri) -> Unit,
        onFailure: () -> Unit
    ) {
        val userRef = storage.child(courseId).child(uri.lastPathSegment ?: "$courseId-${uri}")
        val uploadTask = userRef.putFile(uri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            userRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                onSuccess(downloadUri)
            } else {
                onFailure()
            }
        }
    }

    private fun String.updateHashMap(
        messageValue: Map<String, Message>,
        latestMessage: Map<String, Any?>,
        membersValue: Map<String, Member>
    ): HashMap<String, Any> =
        hashMapOf(
            "$messages/$this" to messageValue,
            "$chats/$this" to latestMessage,
            "$members/$this" to membersValue
        )


    override fun removeData() {
        TODO("Not yet implemented")
    }

    override fun removeAllListener() {
        userDatabaseRef.child(messages).removeEventListener(messagesChildEventListener)
    }
}