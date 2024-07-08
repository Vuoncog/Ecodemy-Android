package com.kltn.ecodemy.domain.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.kltn.ecodemy.constant.formatToString
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

class NotificationMapper : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    @RequiresApi(Build.VERSION_CODES.O)
    var time: String = LocalDateTime.now().formatToString()
    var title: String = ""
    var content: String = ""
    var image: String = ""
}

