package com.example.digishop.domain.manager.repository

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry(value:Boolean)

   suspend fun readAppEntry():Flow<Boolean>

}