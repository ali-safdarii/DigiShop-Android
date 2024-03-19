package com.example.digishop.data.remote.responses.product_response

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductMeta(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("meta_key")
    val metaKey: String,
    @SerializedName("meta_value")
    val metaValue: String,
):Parcelable
