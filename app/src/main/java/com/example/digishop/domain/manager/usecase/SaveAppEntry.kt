package com.example.digishop.domain.manager.usecase

import com.example.digishop.domain.manager.repository.LocalUserManager
import javax.inject.Inject

class SaveAppEntry @Inject constructor(private val localUserManager: LocalUserManager) {

    suspend operator fun invoke(value:Boolean) {
        localUserManager.saveAppEntry(value)
    }
}