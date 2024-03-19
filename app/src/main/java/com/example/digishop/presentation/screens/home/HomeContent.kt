package com.example.digishop.presentation.screens.home

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.digishop.R
import com.example.digishop.data.remote.responses.product_response.Category
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.CustomLayout
import com.example.digishop.presentation.screens.home.section.AdsPager
import com.example.digishop.presentation.screens.home.section.AmazingProductList
import com.example.digishop.presentation.screens.home.section.CategoryList
import com.example.digishop.presentation.screens.home.section.ProductGrid
import com.example.digishop.presentation.screens.home.section.TopicList
import com.example.digishop.presentation.theme.backgroundColor
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import com.example.digishop.utils.AdPosition
import com.example.digishop.utils.Utils


@Composable
fun MainContent(
    state: HomeState.Success,
    onCategoryClick: (Category) -> Unit,
    onProductClick: (Product) -> Unit,
    toProductScreen: () -> Unit,
    toSearchScreen: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val (summeryProducts, amazingProducts, bannerItem, categories) = state



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBarInput(toSearchScreen)
        }
    ) { paddingValues ->
        CustomLayout(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            content = {
                LazyColumn(modifier = Modifier.fillMaxSize()) {

                  /*  stickyHeader {
                        SearchBarInput()
                    }*/
                    item {
                        Surface(
                            color = white,
                            shadowElevation = 12.dp,
                            tonalElevation = 12.dp,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AdsPager(bannerItem)
                                TopicList()
                                AmazingProductList(
                                    amazingList = amazingProducts,
                                    onProductClick = onProductClick,
                                    toProductScreen = toProductScreen
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                Spacer(
                                    modifier = Modifier
                                        .height(24.dp)
                                        .fillMaxWidth()
                                )
                                DisplayAds(imageUrl = bannerItem.filter {
                                    it.position == Utils.bannerPositions(
                                        AdPosition.MiddleCenter
                                    )
                                }[0].imageUrl)
                                Spacer(
                                    modifier = Modifier
                                        .height(8.dp)
                                        .fillMaxWidth()
                                )
                                DisplayAds(imageUrl = bannerItem.filter {
                                    it.position == Utils.bannerPositions(
                                        AdPosition.MiddleCenter
                                    )
                                }[1].imageUrl)
                                Spacer(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .fillMaxWidth()
                                )
                                SectionTitle(
                                    title = "گوشی موبایل",
                                    subTitile = "بر اساس بازدید های شما",
                                    horizontalAlignment = Alignment.Start
                                )
                                ProductGrid(
                                    productList = summeryProducts,
                                    onProductClick = onProductClick
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .fillMaxWidth()
                                )

                                SectionTitle(title = " خرید بر اساس دسته بندی")


                                CategoryList(categories = categories, onCategoryClick)

                                Spacer(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .fillMaxWidth()
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .background(MaterialTheme.colorScheme.background)
                                        .fillMaxWidth(),
                                )
                            }
                        }
                    }
                }
            }
        )
    }

}


@Composable
fun SearchBarInput(
    onClick: () -> Unit
) {

    Surface(
        Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(color = white),
        shadowElevation = 12.dp,
        tonalElevation = 12.dp,
        color = white,
    ) {

        Row(
            Modifier
                .clickable { onClick() }
                .padding(8.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(color = backgroundColor, shape = RoundedCornerShape(size = 8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            // horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Rounded.Search, contentDescription = "Searching....",
                modifier = Modifier
                    .padding(start = 12.dp),
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp),
                text = "نام محصول را وارد کنید ... ",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.vazirmatn_regular)),
                    fontWeight = FontWeight(300),
                    color = Color(0xFF454F46),
                    textAlign = TextAlign.Right,
                    letterSpacing = 0.5.sp,

                    )
            )
        }
    }

}

@Composable
private fun DisplayAds(imageUrl: String) {

    Card {
        Box(modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clickable { }) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Inside,

                )

        }
    }
}


@Composable
private fun SectionTitle(
    title: String,
    subTitile: String? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = title, modifier = Modifier.padding(start = 24.dp),
            style = MaterialTheme.typography.titleMedium
        )

        subTitile?.let {
            Text(
                text = it,
                modifier = Modifier.padding(start = 24.dp, top = 4.dp),
                style = MaterialTheme.typography.labelMedium,
                color = textSecondryColor
            )
        }

    }

}
