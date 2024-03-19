package com.example.digishop.data.remote.responses.product_response


import com.example.digishop.domain.product.model.Price
import com.example.digishop.domain.product.model.Product
import com.google.gson.annotations.SerializedName

data class ProductApiModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_sizes")
    val imageSizes: ImageSizes,
    @SerializedName("frozen_number")
    val frozenNumber: Int,
    @SerializedName("height")
    val height: String,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("length")
    val length: String,
    @SerializedName("marketable")
    val marketable: Int,
    @SerializedName("marketable_number")
    val marketableNumber: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("model_name")
    val modelName: String?,
    @SerializedName("price")
    var price: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("sold_number")
    val soldNumber: Int,
    @SerializedName("default_color_id")
    val defualtColorId: Int?,
    @SerializedName("discounts")
    val discounts: Discount?,

    @SerializedName("status")
    val status: Int,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("width")
    val width: String,

    @SerializedName("is_favorite")
    val isFavorite: Boolean,

    //Relations
    @SerializedName("brand")
    val brand: Brand,
    @SerializedName("colors")
    val colorApis: List<ColorApi>,
    @SerializedName("guarantees")
    val guarantees: List<Guarantee>,
    @SerializedName("galleries")
    val galleries: List<Gallery>,
    @SerializedName("category")
    val category: Category,
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("tags")
    val tags: List<Tag>,
    @SerializedName("values")
    val values: List<Value>,
    @SerializedName("metas")
    val metas: List<ProductMeta>,

    )


/*
fun ProductApiModel.mapColor(color: ColorApi): ProductColor {

    return ProductColor(

        id = color.id,

        colorCode = color.colorCode,

        colorName = color.name,

        price = color.priceIncrease,

        isSelected = isSelected(color.toColor()),

        isDefault = color.id == defualtColorId

    )

}
fun ProductApiModel.colors(): List<ProductColor> {

    return colorApis.map { mapColor(it) }

}
fun  ProductApiModel.isSelected(item: ProductColor): Boolean {
    return if(defualtColorId == null) {
        // Select first color by default
        item == colors().first()

    } else {
        item.id == defualtColorId
    }
}
*/



fun ProductApiModel.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        defualtColorId = defualtColorId ?: colorApis.firstOrNull()?.id
        ?: 0, // Set default to the first color ID or 0 if the list is empty
        imageSizes = imageSizes,
      /*  image = ProductImageSize(
            small = imageSizes.indexArray.small,
            medium = imageSizes.indexArray.medium,
            large = imageSizes.indexArray.large,
            current = imageSizes.currentImageUrl
        ),*/
        introduction = introduction,
        price = Price(productPrice = price, discountPrice = discounts?.finalPrice),
        discounts = discounts,
        // colors = colors() ,
        colors = colorApis.map { it.toColor() },
        galleries = galleries,
        metas = metas,
        comments = comments,
        categoryLastTwoParents = ""//category.lastTwoParents ?: "",
    )
}





























