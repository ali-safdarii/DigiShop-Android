package com.example.digishop.data.remote.responses.product_response

import com.example.digishop.di.BASE_URL


/* Sizes= Large:1920x180 , Medium:800x600 Small:150x100 */
data class ImageIndexArray(
    private val large: String,
    private val medium: String,
    private val small: String
) {
    val largeImageUrl: String
        get() = BASE_URL + large

    val mediumImageUrl: String
        get() = BASE_URL + medium

    val smallImageUrl: String
        get() = BASE_URL + small
}