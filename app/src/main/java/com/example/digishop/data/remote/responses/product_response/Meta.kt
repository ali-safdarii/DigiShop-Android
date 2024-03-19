package com.example.digishop.data.remote.responses.product_response


import com.example.digishop.data.remote.responses.product_response.Link
import com.google.gson.annotations.SerializedName

data class Meta(
    /*
        val count: Int? = 0,
        @SerializedName("current_page")
        val page: Int,
        @SerializedName("page_count")
        val pageCount: Int? = 0,
        @SerializedName("per_page")
        val pageSize: Int,
    */


    ///////////////////////

    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("from")
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("links")
    val links: List<Link>,
    @SerializedName("path")
    val path: String,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("to")
    val to: Int,
    @SerializedName("total")
    val total: Int
)
