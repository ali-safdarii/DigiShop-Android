package com.example.digishop.data.remote.responses.product_response

data class Comment(
    val id: Int,
    val title:String?,
    val body: String,
    val parent_id: Int,
    val user_id: Int,
    val commentable_id: Int,
    val commentable_type: String,
    val seen: Int,
    val approved: Int
)
