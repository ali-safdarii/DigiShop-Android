package com.example.digishop.domain.cart.model

data class CartSummery(
    val title:String="",
    val itemCount:Int=0,
    val totalPriceWithoutDiscount:Int=0,
    val totalDiscount:Int=0,
    val total:Int=0,
    val discountPercentage: Double= 0.0
)
