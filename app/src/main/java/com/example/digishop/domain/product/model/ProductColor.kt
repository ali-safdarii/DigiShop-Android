package com.example.digishop.domain.product.model

data class ProductColor(
    val id: Int,
    val colorCode: String,
    val colorName: String,
    val price: Int,
    val isSelected:Boolean = false,
    val isDefault:Boolean = false
){
    override fun toString(): String {
        return "ProductColor(id=$id, colorCode='$colorCode', colorName='$colorName', price=$price, isSelected=$isSelected, isDefault=$isDefault)"
    }
}


