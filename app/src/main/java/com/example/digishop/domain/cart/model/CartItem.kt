package com.example.digishop.domain.cart.model

import com.example.digishop.domain.product.model.Product

class CartItem(
    val cartItemId:Int,
    val product: Product,
    val productDiscount:Int,
    val colorId:Int,
    val colorName: String,
    val colorCode: String,
    val colorPrice:Int,
    val unitPrice:Int = (product.price.productPrice + colorPrice),
    var quantity: Int,
    var finalPrice:Int= unitPrice * quantity,
    var primaryDiscount:Int = productDiscount * quantity,
    var isLoading: Boolean = false,

    ){
    override fun toString(): String {
        return "CartItem(cartItemId=$cartItemId, product=$product, productDiscount=$productDiscount, colorId=$colorId, colorName='$colorName', colorCode='$colorCode', colorPrice=$colorPrice, unitPrice=$unitPrice, quantity=$quantity, finalPrice=$finalPrice, primaryDiscount=$primaryDiscount, isLoading=$isLoading)"
    }
}