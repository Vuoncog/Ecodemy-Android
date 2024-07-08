package com.kltn.ecodemy.domain.repository

import com.kltn.ecodemy.domain.models.NotificationMapper
import kotlinx.coroutines.flow.Flow

interface RealmDataProcess {
    fun getAllData(): Flow<List<NotificationMapper>>
    suspend fun insertData(notificationMapper: NotificationMapper)
}