package com.example.digishop.data.remote.responses.auth

import com.example.digishop.domain.user.model.User
import com.google.gson.annotations.SerializedName


data class AuthResponse(
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("user")
    val userApi: UserApi,
)

fun AuthResponse.toUser(): User {
    return User(
        id = userApi.id,
        email = userApi.email,
        jwtToken = jwt
    )
}