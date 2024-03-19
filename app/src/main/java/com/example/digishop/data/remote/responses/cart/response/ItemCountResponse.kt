package com.example.digishop.data.remote.responses.cart.response

import com.google.gson.annotations.SerializedName

data class ItemCountResponse(
    @SerializedName("items_count")
    val itemsCount: Int,

)
