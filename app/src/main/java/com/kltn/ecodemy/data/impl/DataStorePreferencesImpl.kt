package com.kltn.ecodemy.data.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kltn.ecodemy.constant.Constant.PREFERENCES_LANGUAGE_KEY
import com.kltn.ecodemy.constant.Constant.PREFERENCES_NAME
import com.kltn.ecodemy.domain.repository.DataStorePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStorePreferencesImpl(context: Context): DataStorePreferences {
    private val dataStore = context.dataStore

    override suspend fun <T> saveData(data: T, pref: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences[pref] = data
        }
    }

    override fun <T> readData(pref: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
            .map { preference ->
                preference[pref]
            }
    }
}

object PreferenceKey {
    val languagePref = stringPreferencesKey(name = PREFERENCES_LANGUAGE_KEY)
}