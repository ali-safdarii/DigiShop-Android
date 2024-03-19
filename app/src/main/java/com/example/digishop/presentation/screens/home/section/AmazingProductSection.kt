package com.example.digishop.presentation.screens.home.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.digishop.R
import com.example.digishop.data.remote.responses.amazing_products.response.AmazingData
import com.example.digishop.data.remote.responses.product_response.toProduct
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.PriceRow
import com.example.digishop.presentation.theme.primaryColor
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.textSecondryColor
import java.util.UUID

@Composable
 fun AmazingProductList(
    amazingList: List<AmazingData>,
    onProductClick: (Product) -> Unit,
    toProductScreen: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .paint(
                // Replace with your image id
                painterResource(id = R.drawable.amazing_sale_bg_red),
                contentScale = ContentScale.FillWidth
            ),

        contentAlignment = Alignment.Center
    ) {


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            //modifier = Modifier.padding(PaddingValues(24.dp))
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(150.dp)
                        .height(370.dp),
                ) {
                    Image(
                        modifier = Modifier.fillMaxHeight(),
                        painter = painterResource(id = R.drawable.box_amazing),
                        contentDescription = "box_amazing_logo"
                    )

                }
            }
            items(items = amazingList, key = { data ->
                data.id
            }) { data ->
                AmazingProductItem(amazingData = data, onProductClick = onProductClick)

            }

            item {
                Card(
                    modifier = Modifier
                        .padding(
                            start = 8.dp, end = 8.dp
                        )
                        .fillMaxSize()
                        .clickable {
                            toProductScreen()
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .width(175.dp)
                            .height(370.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {

                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleLeft,
                            contentDescription = UUID.randomUUID().toString(),
                            tint = primaryColor,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)

                        )

                        Text(
                            text = "مشاهده همه",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.vazirmatn_medium)),
                                fontSize = 14.sp

                            )
                        )
                    }
                }
            }

        }

    }

}

@Composable
private fun AmazingProductItem(amazingData: AmazingData, onProductClick: (Product) -> Unit) {

    val product = amazingData.productApiModel.toProduct()
    Card(modifier = Modifier
        .clickable { onProductClick(product) }
        // .padding(top = 0.dp, bottom = 16.dp, start = 8.dp)
        .fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)

    ) {
        Column(
            modifier = Modifier
                .width(180.dp)
                .height(370.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            val requestBuilder = ImageRequest.Builder(LocalContext.current)
                .data(product.imageSizes.indexArray.mediumImageUrl).scale(Scale.FIT)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .crossfade(true).build()

            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            AsyncImage(
                model = requestBuilder,
                contentDescription = product.name,
                modifier = Modifier
                    .height(150.dp)
                    .padding(8.dp),
                error = painterResource(R.drawable.not_found),
                placeholder = painterResource(R.drawable.not_found),
            )

            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                text = product.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .height(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = UUID.randomUUID().toString(),
                    tint = skyBlue,
                )

                Spacer(
                    modifier = Modifier.width(4.dp)

                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "موجود در انبار دیجی کالا",
                    style = MaterialTheme.typography.labelSmall,
                    color = textSecondryColor,
                    textAlign = TextAlign.Right,
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )


            PriceRow(discount = product.discounts, originalPrice = product.price.productPrice)


        }
    }


}
