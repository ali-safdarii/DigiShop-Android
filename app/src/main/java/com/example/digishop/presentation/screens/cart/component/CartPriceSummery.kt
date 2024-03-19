package com.example.digishop.presentation.screens.cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digishop.domain.cart.model.CartSummery
import com.example.digishop.presentation.component.priceStyle
import com.example.digishop.presentation.theme.black
import com.example.digishop.presentation.theme.primaryColor
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white


val hieghtDp = 6.dp

@Composable
fun CartPriceSummery(
    cartSummery: CartSummery
) {
    Column(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 24.dp)
            .fillMaxSize()
            .background(white)
            .padding(PaddingValues(12.dp)),
    ) {
        SummeryHeader(cartSummery)



        SummaryItem(
            title = "قیمت کالا ها",
            total = cartSummery.totalPriceWithoutDiscount,
            heightDp = 20.dp
        )

        // SummaryItem(title = "مبلغ بیمه", value = "418000 تومان", heightDp = hieghtDp)

        SummaryItem(
            title = "تخفیف کالاها",
            total = cartSummery.totalDiscount,
            color = primaryColor,
            heightDp = hieghtDp
        )

        SummaryItem(
            title = "جمع سبد خرید",
            total = cartSummery.total,
            heightDp = hieghtDp
        )

        SummerySpacer(hieghtDP = 8.dp)


    }
}


@Composable
private fun SummaryItem(
    title: String,
    total: Int,
    color: Color = Color.Unspecified,
    heightDp: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = heightDp)
            .padding(PaddingValues(8.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = textSecondryColor,
        )
        Text(
            text = priceStyle(totalPrice = total, fontSize = 15.sp),
            style = MaterialTheme.typography.bodyMedium,
            color = if (color != Color.Unspecified) color else black

        )

    }
}


@Composable
private fun SummeryHeader(cartSummery: CartSummery) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        //  horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "خلاصه سبد",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(.9f)
        )
        Text(
            text = "${cartSummery.itemCount} کالا ",
            style = MaterialTheme.typography.labelMedium,
            color = textSecondryColor,
            modifier = Modifier
                .weight(.1f)
        )
    }
}


@Composable
private fun SummerySpacer(hieghtDP: Dp) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(hieghtDP)
    )
}