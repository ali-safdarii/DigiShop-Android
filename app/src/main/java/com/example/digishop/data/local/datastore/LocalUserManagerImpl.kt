package com.example.digishop.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.digishop.domain.manager.repository.LocalUserManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalUserManager {

    private val Context.dataStore by preferencesDataStore(
        name = USER_SETTINGS
    )

    override suspend fun saveAppEntry(value:Boolean) {
        context.dataStore.edit { preferences ->
            preferences[APP_ENTRY] = value
        }
    }

    override suspend fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[APP_ENTRY] ?: false
            }
    }

    companion object {
        private val APP_ENTRY = booleanPreferencesKey("appEntry")
        private const val USER_SETTINGS = "user_settings"
    }
}

