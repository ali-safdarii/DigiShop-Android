package com.example.digishop.data.local.proto

import androidx.datastore.core.DataStore
import com.example.digishop.DataStoreToken
import com.example.digishop.domain.token.model.JwtToken
import com.example.digishop.domain.token.model.Token
import com.example.digishop.domain.token.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ProtoTokenRepository @Inject constructor(
    private val tokenDataStore: DataStore<DataStoreToken>,
) : TokenRepository {
    override suspend fun storeToken(token: Token) {
        tokenDataStore.updateData { dataStoreToken ->
            dataStoreToken
                .toBuilder()
                .setAuthToken(token.jwt.value)
                .build()
        }
    }

    override suspend fun clearToken() {
        tokenDataStore.updateData { dataStoreToken ->
            dataStoreToken.defaultInstanceForType
        }
    }

    override fun observeToken(): Flow<Token?> {
        return tokenDataStore.data
            .map { dataStoreToken ->
                Timber.d("Mapped token: ${dataStoreToken.toToken()}")
                dataStoreToken.toToken()
            }
    }


    private fun DataStoreToken.toToken(): Token? {
        return if (this == DataStoreToken.getDefaultInstance()) {
            null
        } else {
            Token(
                jwt = JwtToken(this.authToken),
            )
        }

    }

}