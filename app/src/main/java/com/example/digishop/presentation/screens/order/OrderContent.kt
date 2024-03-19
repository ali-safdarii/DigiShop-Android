package com.example.pickyshop.presentation.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.digishop.R
import com.example.digishop.domain.cart.model.CartItem
import com.example.digishop.domain.cart.model.CartSummery
import com.example.digishop.presentation.component.StickyBottom
import com.example.digishop.presentation.component.TextIconButton
import com.example.digishop.presentation.screens.cart.component.CartPriceSummery
import com.example.digishop.presentation.screens.order.section.OrderItemListSummery
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white

@Composable
fun OrderContent(
    modifier: Modifier,
    cartItemList: List<CartItem>,
    cartSummery: CartSummery,
    onClick: () -> Unit,
) {








        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxSize()
                    .weight(1f)
            ) {

                item {

                    UserAddress()
                }

                item {

                    OrderItemListSummery(cartItemList)

                }


                item {
                    CartPriceSummery(cartSummery = cartSummery)
                }

            }

            StickyBottom(
                onClick = onClick,
                buttonText = stringResource(id = R.string.continue_to_buy),
                label = stringResource(id = R.string.final_order_total),
                cartSummery = cartSummery
            )
            // OrderBottomSection(cartSummery = cartSummery, toPaymentScreen = {})
        }

}

@Composable
private fun UserAddress() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(white)
            .padding(PaddingValues(16.dp))
    ) {
        Text(
            text = stringResource(R.string.send_label),
            style = MaterialTheme.typography.bodySmall,
            color = textSecondryColor,
            modifier = Modifier.padding(start = 32.dp)

        )

        Row(
            Modifier
                .padding(top = 4.dp)
        ) {
            Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "")
            Text(
                text = stringResource(R.string.user_address),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 12.dp)
            )

        }

        TextIconButton(
            modifier = Modifier
                .align(Alignment.End),
            onclick = {},
            text = stringResource(R.string.edit_address_label),
        )

    }
}
