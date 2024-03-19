package com.example.digishop.presentation.screens.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.digishop.R
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.NonlazyGrid
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.textHintError
import java.util.UUID


@Composable
 fun ProductGrid(productList: List<Product>, onProductClick: (Product) -> Unit) {
    val itemCount = productList.size


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NonlazyGrid(
                columns = 3,
                itemCount = productList.take(9).size,
                modifier = Modifier.padding(16.dp)
            ) { index ->
                val product = productList[index]
                val isFirstColumn = index % 3 == 0
                val isCenterColumn = index % 3 == 1
                val isLastRow = index >= itemCount - 4 // Assuming 4 columns
                Box(modifier = Modifier
                    .background(Color.White)
                    //.width(250.dp)
                    .wrapContentHeight()
                    .clickable {
                        onProductClick(product)

                    }
                    .drawWithContent {
                        // Draw right and bottom dividers
                        drawContent()


                        // Draw right divider for the first column and center column
                        if (isFirstColumn || isCenterColumn) {
                            drawLine(
                                color = dividerColor,
                                start = Offset(size.width, 0f),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                        }

                        // Omit bottom divider for the last row
                        if (!isLastRow) {
                            drawLine(
                                color = dividerColor,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                    }) {
                    // Your existing Box content here
                    val requestBuilder = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageSizes.indexArray.mediumImageUrl).scale(Scale.FIT)
                        .size(Size.ORIGINAL).crossfade(true).build()

                    AsyncImage(
                        modifier = Modifier
                            .padding(8.dp),
                        model = requestBuilder,
                        contentDescription = product.name,
                        error = painterResource(R.drawable.not_found),

                        )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "مشاهده بیش از 200 کالا ", style = TextStyle(
                        color = textHintError, fontWeight = FontWeight.W500, fontSize = 14.sp
                    )
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = UUID.randomUUID().toString(),
                    tint = textHintError
                )

            }

            Spacer(modifier = Modifier.height(6.dp))
        }

}