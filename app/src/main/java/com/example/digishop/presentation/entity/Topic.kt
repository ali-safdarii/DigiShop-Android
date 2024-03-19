package com.example.digishop.presentation.entity

import androidx.annotation.DrawableRes
import com.example.digishop.R

data class Topic(
    val title: String,
    @DrawableRes val image: Int
)

val topics = listOf(
    Topic(
        title = "ارسال رایگان",
        image = R.drawable.ic_flash_icon,
    ),

    Topic(
        title = "سوپرمارکت",
        image = R.drawable.ic_bill_icon,
    ),

    Topic(
        title = "بازی",
        image = R.drawable.ic_game_icon,
    ),


    Topic(
        title = "خرید برنده",
        image = R.drawable.ic_gift_icon,
    ),

    Topic(
        title = "بیشتر",
        image = R.drawable.ic_discover,
    ),
)