package com.example.digishop.domain.manager.usecase

import com.example.digishop.domain.manager.repository.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(private val localUserManager: LocalUserManager) {
    suspend operator fun invoke(): Flow<Boolean> = localUserManager.readAppEntry()
}