package com.example.digishop.presentation.screens.details.sections

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.digishop.R
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.CachedPagerImage
import com.example.digishop.presentation.component.CustomHorizontalIndicator
import com.example.digishop.presentation.theme.colorRating
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.mediumeGray
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.white
import java.util.UUID

@Composable
fun TopSection(
    toGalleryScreen: (Product) -> Unit,
    product: Product,
) {

    Surface(
        color = white
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            DetailsGallerySlider(
                onProductClick = { toGalleryScreen(product) },
                product = product,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {


                ClickableText(
                    text = AnnotatedString(product.categoryLastTwoParents),
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = skyBlue,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.vazirmatn_regular))
                    ),
                    onClick = { },
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                )


                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge,
                    softWrap = true,
                    modifier = Modifier
                        .padding(end = 16.dp, start = 16.dp, top = 8.dp)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                )




                Row(Modifier.padding(start = 16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = UUID.randomUUID().toString(),
                            tint = mediumeGray,
                            modifier = Modifier.size(6.dp)
                        )
                        ClickableText(
                            text = AnnotatedString("1387 دیدگاه کاربران"),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = skyBlue,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(Font(R.font.vazirmatn_regular))
                            ),
                            onClick = { },
                            modifier = Modifier.padding(start = 8.dp)
                        )

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 16.dp, start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = UUID.randomUUID().toString(),
                            tint = mediumeGray,
                            modifier = Modifier.size(6.dp)
                        )
                        ClickableText(
                            text = AnnotatedString("368 پرسش و پاسخ"),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = skyBlue,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(Font(R.font.vazirmatn_regular))
                            ),
                            onClick = { },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }


                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbUp,
                        contentDescription = UUID.randomUUID().toString(),
                        tint = colorRating,
                        modifier = Modifier.size(14.dp)
                    )

                    Text(
                        "86% (680 نفر) از خریداران این کالا را پیشنهاد کرده اند",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Divider(
                    color = dividerColor,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )


            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsGallerySlider(
    product: Product,
    onProductClick: (Product) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = {
        product.galleries.size
    })
    val pageCount = product.galleries.size

    if (product.galleries.isNotEmpty())
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            // Use a Box to align items
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Center the HorizontalPager
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clickable { onProductClick(product) }
                            .background(Color.White)
                    ) {
                        CachedPagerImage(
                            page,
                            imageUrl = product.galleries[page].imageSizes.indexArray.mediumImageUrl,
                            content = {
                                SliderItem(uri = it)
                            })
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                CustomHorizontalIndicator(
                    currentPage = pagerState.currentPage,
                    pageCount = pageCount,
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Align to the top start of the parent
                        .height(50.dp)
                        .width(((6 + 16) * 2 + 3 * (10 + 16)).dp),
                )

            }
        }
    else
        Image(
            painter = painterResource(id = R.drawable.not_found),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentScale = ContentScale.Inside,
        )

}


@Composable
private fun SliderItem(uri: Uri) {

    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {


            AsyncImage(
                model = uri,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
              //  contentScale = ContentScale.Inside,
                placeholder = painterResource(id = R.drawable.not_found)
            )
        }

    }
}

