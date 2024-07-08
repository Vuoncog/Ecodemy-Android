package com.kltn.ecodemy.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    suspend fun <T>saveData(data: T, pref: Preferences.Key<T>)
    fun <T>readData(pref: Preferences.Key<T>): Flow<T?>
}