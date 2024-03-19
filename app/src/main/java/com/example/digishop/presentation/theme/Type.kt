package com.example.digishop.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.digishop.R

// SummeryScreen CenterALignedTopBar TextStyle
val summeryTopBarTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.digikala)),
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp,
    color = white,
)


private val vazirFamily = FontFamily(
    Font(R.font.vazirmatn_light, FontWeight.Light),
    Font(R.font.vazirmatn_regular, FontWeight.Normal),
    Font(R.font.vazirmatn_medium, FontWeight.Medium),
    Font(R.font.vazirmatn_bold, FontWeight.Bold),
)

val vazirmatnTypography = Typography(


    titleLarge = TextStyle(
        // used for  title top-appbar
        fontSize = 17.sp, fontWeight = FontWeight.Medium, fontFamily = vazirFamily
    ),

    titleMedium = TextStyle(
        fontSize = 18.sp, fontWeight = FontWeight.Normal, fontFamily = vazirFamily
    ),

    titleSmall = TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = vazirFamily
    ),

    bodyLarge = TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.Medium, fontFamily = vazirFamily
    ),

    bodyMedium = TextStyle(
        fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = vazirFamily
    ),

    bodySmall = TextStyle(
        fontSize = 12.sp, fontWeight = FontWeight.Medium, fontFamily = vazirFamily
    ),

    labelLarge = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = vazirFamily,
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp, fontWeight = FontWeight.Normal, fontFamily = vazirFamily
    ),

    labelSmall = TextStyle(
        fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = vazirFamily
    ),

    )

