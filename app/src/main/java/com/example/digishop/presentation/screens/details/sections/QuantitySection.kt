package com.example.digishop.presentation.screens.details.sections

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.digishop.R
import com.example.digishop.data.remote.responses.product_response.Discount
import com.example.digishop.presentation.component.CBadgeBox
import com.example.digishop.presentation.component.PrimaryButton
import com.example.digishop.presentation.component.QtyButtonIcon
import com.example.digishop.presentation.component.QtyLoading
import com.example.digishop.presentation.component.priceStyle
import com.example.digishop.presentation.screens.details.ButtonState
import com.example.digishop.presentation.screens.details.DetailsState
import com.example.digishop.presentation.theme.gray
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.utils.priceFormat

enum class QtyState {
    DELETE_FROM_CART, DECREASE, INITIAL
}


@Composable
fun UpdateOrInsertProductToCart(
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onDecrement: () -> Unit,
    onIncrease: () -> Unit,
    state: DetailsState,
) {

    when (state.buttonState) {
        ButtonState.ADD_TO_CART -> {

            PrimaryButton(
                onClick = { onClick() },
                text = stringResource(id = R.string.adding_to_cart)
            )

        }

        ButtonState.UPDATE_QUANTITY -> {
            Row(
                modifier = Modifier
                    .width(160.dp)
                    .border(
                        width = 1.dp,
                        color = gray,
                        shape = RoundedCornerShape(8.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                QtyButtonIcon(
                    onClick = { onIncrease() },
                    icon = R.drawable.round_add_24
                )


                QtyLoading(isLoading = state.isLoading, qty = state.qty)

                DeleteOrDecrease(
                    qtyState = state.qtyState,
                    qty = state.qty,
                    onDecrement = onDecrement,
                    onDelete = onDelete
                )


            }
        }
    }
}




@Composable
fun DisplayPriceColumn(
    discounts: Discount?,
    productPrice: Int,
    totalPrice: Int,
    modifier: Modifier
) {
    Column(
        modifier = modifier

    ) {
        discounts?.let {
            Row {

                CBadgeBox(
                    value = it.percentage,
                    modifier = Modifier.padding(end = 4.dp)
                )

                Text(
                    text = priceFormat(productPrice.toDouble()), // original price
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    textDecoration = TextDecoration.LineThrough,
                    color = textSecondryColor,
                )


            }


            Text(
                text = priceStyle(totalPrice)
            )


        } ?: run {
            // If discounts are null or not found, display the original price without strikethrough
            Text(
                text = "$totalPrice تومان ", //amount price with calculate color item
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }

}

@Composable
private fun DeleteOrDecrease(
    qtyState: QtyState,
    qty: Int,
    onDecrement: () -> Unit,
    onDelete: () -> Unit
) {
    when (qtyState) {
        QtyState.INITIAL -> {

            if (qty > 1) {

                QtyButtonIcon(
                    onClick = { onDecrement() },
                    icon = R.drawable.baseline_remove_24
                )
            } else {

                QtyButtonIcon(
                    onClick = { onDelete() },
                    icon = R.drawable.delete_24px
                )
            }

        }

        QtyState.DELETE_FROM_CART -> {
            QtyButtonIcon(
                onClick = { onDelete() },
                icon = R.drawable.delete_24px
            )
        }

        QtyState.DECREASE -> {
            QtyButtonIcon(
                onClick = { onDecrement() },
                icon = R.drawable.baseline_remove_24
            )
        }


    }
}