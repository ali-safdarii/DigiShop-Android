package com.example.digishop.domain.user.model



data class User(
    val id:Int,
    val email:String,
    val jwtToken:String,
)
