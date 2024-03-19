package com.example.digishop.domain.product.model

import com.example.digishop.data.remote.responses.product_response.Comment
import com.example.digishop.data.remote.responses.product_response.Discount
import com.example.digishop.data.remote.responses.product_response.Gallery
import com.example.digishop.data.remote.responses.product_response.ImageSizes
import com.example.digishop.data.remote.responses.product_response.ProductMeta
import com.example.digishop.di.BASE_URL

data class Product(
    val id: Int,
    val name: String,
    val imageSizes: ImageSizes,
   // val image: ProductImageSize,
    val introduction: String,
    var price: Price,
    val defualtColorId: Int,
    val discounts: Discount?,
    val colors: List<ProductColor>,
    val galleries: List<Gallery>,
    val metas: List<ProductMeta>,
    val comments: List<Comment>,
    val categoryLastTwoParents: String,
)

data class Price(
    val productPrice: Int,
    val discountPrice: Int?,

    ) {
    private fun productPrice(): Int = discountPrice ?: productPrice

    fun totalPrice(colorPrice: Int): Int = colorPrice + productPrice()

}


class ProductImageSize(
    private val medium: String,
    private val small: String,
    private val large: String,
    val current: String
) {
    val largeImageUrl: String
        get() = BASE_URL + this.large

    val mediumImageUrl: String
        get() = BASE_URL + this.medium

    val smallImageUrl: String
        get() = BASE_URL + this.small
}




