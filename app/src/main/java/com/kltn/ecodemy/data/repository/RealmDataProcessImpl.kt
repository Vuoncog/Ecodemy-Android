package com.kltn.ecodemy.data.repository

import com.kltn.ecodemy.domain.models.NotificationMapper
import com.kltn.ecodemy.domain.repository.RealmDataProcess
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealmDataProcessImpl @Inject constructor(
    private val realm: Realm
) : RealmDataProcess {

    override fun getAllData(): Flow<List<NotificationMapper>> {
        return realm.query(NotificationMapper::class).asFlow().map { it.list }
    }

    override suspend fun insertData(notificationMapper: NotificationMapper) {
        realm.write { copyToRealm(notificationMapper) }
    }
}