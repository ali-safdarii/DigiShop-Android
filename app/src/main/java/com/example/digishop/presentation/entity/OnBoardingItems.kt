package com.example.digishop.presentation.entity

import androidx.annotation.DrawableRes
import com.example.digishop.R

data class OnBoardingItems(
    val title: String? = null,
    val description: String,
    @DrawableRes val image: Int

)

val onboardPages = listOf(
    OnBoardingItems(
        title = "title 1",
        description = "Make a quick transaction with someone",
        image=R.drawable.undraw_intro_1
    ),
    OnBoardingItems(
        title = "title 2",
        description = "Pick a contact directly from your contact ",
        image=R.drawable.undraw_intro_2
    ),
    OnBoardingItems(
        title = "title 3",
        description = "We will never know your Momo PIN in anyway",
        image=R.drawable.undraw_intro_3
    )
)
