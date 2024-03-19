package com.example.digishop.data.remote.responses.cart.response


import com.example.digishop.domain.cart.model.CartItem
import com.example.digishop.data.remote.responses.product_response.ColorApi
import com.example.digishop.data.remote.responses.product_response.ProductApiModel
import com.example.digishop.data.remote.responses.product_response.toProduct
import com.example.digishop.domain.cart.model.CartSummery
import com.google.gson.annotations.SerializedName

data class CartResponseApi(
    @SerializedName("id")
    val id: Int,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("items")
    val items: List<CartItemApi>
)

class CartItemApi(
    val id: Int,
    var qty: Int,
    val product: ProductApiModel,
    val color: ColorApi,
    var isLoading: Boolean = false,
)


fun CartResponseApi.toCartItems(): List<CartItem> {
    return items.map { it.toCartItem() }
}


fun cartSummery(): CartSummery {
    return CartSummery(

    )
}



fun CartItemApi.toCartItem(): CartItem {
    return CartItem(
        cartItemId = id,
        productDiscount = product.discounts?.discountAmount ?: 0,
        product = product.toProduct(),
        colorId = color.id,
        colorName = color.name,
        colorPrice = color.priceIncrease,
        quantity = qty,
        colorCode = color.colorCode,
    )
}


