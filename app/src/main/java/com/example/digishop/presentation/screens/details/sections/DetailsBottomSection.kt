package com.example.digishop.presentation.screens.details.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.screens.details.DetailsState
import com.example.digishop.presentation.theme.lightGray

@Composable
fun DetailsBottomSection(
    onAdd: () -> Unit,
    onDelete: () -> Unit,
    onDecrement: () -> Unit,
    onIncrease: () -> Unit,
    product: Product,
    totalPrice: Int,
    state: DetailsState,
) {
    
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            border = BorderStroke(width = 1.dp, color = lightGray),
            shape = RoundedCornerShape(0.dp)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    UpdateOrInsertProductToCart(
                        state = state,
                        onDecrement = onDecrement,
                        onIncrease = onIncrease,
                        onDelete = onDelete,
                        onClick = onAdd
                    )


                    Spacer(modifier = Modifier.width(8.dp))


                    DisplayPriceColumn(
                        discounts = product.discounts,
                        totalPrice = totalPrice,
                        productPrice = product.price.productPrice,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 12.dp)
                    )
                }
            }
        }

}