package com.example.digishop.presentation.screens.cart.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.ehsanmsz.mszprogressindicator.progressindicator.BallBeatProgressIndicator
import com.example.digishop.R
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.primary500
import com.example.digishop.presentation.theme.primaryColor
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import com.example.digishop.utils.Dimension
import com.example.digishop.utils.Dimension.cartSpaceHeightSize
import com.example.digishop.utils.parseColor
import com.example.digishop.domain.cart.model.CartItem
import com.example.digishop.domain.cart.model.CartSummery
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.QtyButtonIcon
import com.example.digishop.presentation.component.StickyBottom
import com.example.digishop.presentation.component.TextIconButton
import com.example.digishop.presentation.component.bottomBorder
import com.example.digishop.presentation.component.priceStyle


@Composable
fun ColumnScope.ShoppingCartList(
    onIncrement: (CartItem) -> Unit,
    onDecrement: (CartItem) -> Unit,
    onDelete: (CartItem) -> Unit,
    cartItemList: List<CartItem>,
    cartSummery: CartSummery,
    navToOrder: () -> Unit,
    navToDetails: (Product) -> Unit,

) {

    if (cartItemList.isNotEmpty()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(1f)
        ) {

            items(
                items = cartItemList,
                key = { item ->
                    item.cartItemId
                }
            ) { cartItem ->

                CartItem(
                    cartItem = cartItem,
                    onIncrement = { onIncrement(it) },
                    onDecrement = { onDecrement(it) },
                    onDelete = { onDelete(it) },
                    onItemClick = navToDetails
                )

            }

            item {
                CartPriceSummery(cartSummery = cartSummery)
            }
        }
        StickyBottom(
            onClick = navToOrder,
            buttonText = stringResource(id = R.string.continue_to_buy),
            label = stringResource(id = R.string.final_cart_total),
            cartSummery = cartSummery
        )
    } else {
        EmptyCart()

    }
}


@Composable
private fun CartItem(
    cartItem: CartItem,
    onIncrement: (CartItem) -> Unit,
    onDecrement: (CartItem) -> Unit,
    onDelete: (CartItem) -> Unit,
    onItemClick: (Product) -> Unit

) {
    val color = parseColor(cartItem.colorCode)

    Column(
        modifier = Modifier
            .clickable { onItemClick(cartItem.product) }
            .background(white)
            .fillMaxWidth()
            .bottomBorder(1.dp, dividerColor)
    ) {


        Row(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .padding(PaddingValues(6.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val requestBuilder = ImageRequest.Builder(LocalContext.current)
                .data(cartItem.product.imageSizes.currentImageUrl).scale(Scale.FIT)
                .size(Size.ORIGINAL).crossfade(true).build()

            AsyncImage(
                model = requestBuilder,
                contentDescription = cartItem.product.id.toString(),
                modifier = Modifier
                    .weight(0.4f)
                    .padding(PaddingValues(20.dp)),
                contentScale = ContentScale.Inside,
                error = painterResource(R.drawable.not_found),
            )


            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .weight(0.6f),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        cartItem.product.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 6.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis

                    )
                }


                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Spacer(modifier = Modifier.width(Dimension.xs))

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(Dimension.sm))
                    Text(
                        cartItem.colorName,
                        style = MaterialTheme.typography.labelSmall,
                        color = textSecondryColor
                    )
                }



                Row(
                    modifier = Modifier
                        .padding(top = cartSpaceHeightSize)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null,
                        modifier = Modifier.size(Dimension.cartIconSize),
                    )
                    Spacer(modifier = Modifier.width(Dimension.sm))

                    Text(
                        "گارانتی",
                        style = MaterialTheme.typography.labelSmall,
                        color = textSecondryColor,
                    )
                }



                Row(
                    modifier = Modifier
                        .padding(top = cartSpaceHeightSize)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = null,
                        modifier = Modifier.size(Dimension.cartIconSize),
                    )
                    Spacer(modifier = Modifier.width(Dimension.sm))
                    Text(
                        text = "موجود در انبار دیجی کالا",
                        style = MaterialTheme.typography.labelSmall,
                        color = textSecondryColor
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = cartSpaceHeightSize)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Store,
                        contentDescription = null,
                        modifier = Modifier.size(Dimension.cartIconSize),
                        tint = skyBlue
                    )
                    Spacer(modifier = Modifier.width(Dimension.sm))
                    Text(
                        "فارما",
                        style = MaterialTheme.typography.labelSmall,
                        color = textSecondryColor
                    )
                }

            }


        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(PaddingValues(8.dp))
        ) {
            Box(
                modifier = Modifier.weight(.4f), contentAlignment = Alignment.Center
            ) {

                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .border(
                            width = 1.dp, color = dividerColor, shape = RoundedCornerShape(8.dp)
                        ),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    QtyButtonIcon(
                        onClick = {
                            onIncrement(cartItem)
                        }, icon = R.drawable.round_add_24
                    )



                    AnimatedVisibility(visible = cartItem.isLoading) {
                        BallBeatProgressIndicator(
                            modifier = Modifier.padding(
                                start = 2.dp, end = 2.dp
                            ),
                            color = primary500,
                            ballCount = 3,
                            maxBallDiameter = 12.dp,
                        )
                    }




                    if (!cartItem.isLoading)
                        Text(
                            text = "${cartItem.quantity}",
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = primaryColor
                        )

                    if (cartItem.quantity > 1) {
                        QtyButtonIcon(
                            onClick = {
                                onDecrement(cartItem)
                            }, icon = R.drawable.baseline_remove_24
                        )
                    } else {
                        QtyButtonIcon(
                            onClick = {
                                onDelete(cartItem)
                            },
                            icon = R.drawable.delete_24px,
                        )
                    }


                }


            }

            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(.6f)
            ) {

                Column {

                    if (cartItem.primaryDiscount != 0) {
                        Text(
                            text = "${cartItem.primaryDiscount} تومان تخفیف ", // original price
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                            color = primaryColor,
                        )
                    }



                    Text(
                        text = priceStyle(cartItem.finalPrice)
                    )


                }
            }

        }


        TextIconButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp),
            onclick = {},
            text = "ذخیره در لیست خرید بعدی"
        )


    }

}

