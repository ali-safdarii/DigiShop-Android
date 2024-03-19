package com.example.digishop.presentation.screens.order.section

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.digishop.R
import com.example.digishop.domain.cart.model.CartItem
import com.example.digishop.presentation.theme.lightGray
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import com.example.digishop.utils.parseColor

@Composable
 fun OrderItemListSummery(cartItemList: List<CartItem>) {
    Column(
        Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .background(white)
    ) {
        Text(
            "شیوه و زمان ارسال",
            modifier = Modifier.padding(start = 16.dp, top = 12.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 15.sp
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 6.dp),
            text = "${cartItemList.size} کالا ",
            style = MaterialTheme.typography.labelMedium,
            color = textSecondryColor,
        )

        Card(
            modifier = Modifier
                .padding(4.dp)
                .padding(8.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            border = BorderStroke(width = 1.dp, color = lightGray),
            shape = RoundedCornerShape(6.dp)
        ) {
            LazyRow(
                //modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(
                    items = cartItemList,
                    key = { item ->
                        item.cartItemId
                    }
                ) { cartItem ->
                    Column(
                        modifier = Modifier.background(white),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val color = parseColor(cartItem.colorCode)

                        val requestBuilder = ImageRequest.Builder(LocalContext.current)
                            .data(cartItem.product.imageSizes.indexArray.mediumImageUrl)
                            .scale(Scale.FIT)
                            .size(Size.ORIGINAL)
                            .crossfade(true).build()

                        AsyncImage(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(PaddingValues(4.dp)),
                            model = requestBuilder,
                            contentDescription = cartItem.product.id.toString(),
                            contentScale = ContentScale.Inside,
                            error = painterResource(R.drawable.not_found),
                        )

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(10.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .background(color)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = cartItem.colorName,
                                style = MaterialTheme.typography.labelSmall,
                                color = textSecondryColor
                            )
                        }
                    }


                }
            }
        }

    }
}

