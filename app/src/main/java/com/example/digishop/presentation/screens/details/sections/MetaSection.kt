package com.example.digishop.presentation.screens.details.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.gray
import com.example.digishop.presentation.theme.mediumeGray
import com.example.digishop.presentation.theme.white

@Composable
fun MetasAndReviewSection(
    product: Product,
    toMetaScreen: (Product) -> Unit,
    toReviewScreen: (Product) -> Unit
) {


        Surface(
            modifier = Modifier
                .padding(top = 12.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            color = white

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(
                    "ویژگی های محصول",
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {


                    product.metas.take(5).forEach { meta ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(.5f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Circle,
                                    contentDescription = "${meta.id}",
                                    tint = mediumeGray,
                                    modifier = Modifier.size(6.dp)
                                )

                                Text(
                                    meta.metaKey + " : ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = gray,
                                    modifier = Modifier.padding(start = 8.dp)
                                )

                            }


                            Text(
                                meta.metaValue,
                                modifier = Modifier.weight(.5f),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)

                )

                TopBorderBox(content = {

                    Text(
                        "مشخصات فنی",
                        // fontSize = 16.sp
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "مشخصات فنی",
                        tint = gray,
                        modifier = Modifier.size(14.dp)
                    )

                }, onClick = {
                    toMetaScreen(product)
                })


                TopBorderBox(content = {
                    Text(
                        "معرفی اجمالی محصول",
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "معرفی اجمالی محصول",
                        tint = gray,
                        modifier = Modifier.size(14.dp)
                    )


                }, onClick = {
                    toReviewScreen(product)
                })



                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)

                )

            }


        }


}


@Composable
private fun TopBorderBox(
    modifier: Modifier = Modifier,
    borderColor: Color = dividerColor,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val halfStrokeWidth = borderWidth / 2
                val startY = halfStrokeWidth.toPx()
                drawLine(
                    color = borderColor,
                    start = Offset(0f, startY),
                    end = Offset(size.width, startY),
                    strokeWidth = borderWidth.toPx()
                )
            }
            .clickable {
                onClick()
            },
        //  contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(PaddingValues(16.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }

    }
}