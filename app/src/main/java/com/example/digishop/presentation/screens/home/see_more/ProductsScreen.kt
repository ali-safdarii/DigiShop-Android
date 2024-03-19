package com.example.digishop.presentation.screens.home.see_more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotatePulseProgressIndicator
import com.example.digishop.R
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.ErrorState
import com.example.digishop.presentation.component.LoadingState
import com.example.digishop.presentation.component.PriceRow
import com.example.digishop.presentation.component.ProductScreenTopAppBar
import com.example.digishop.presentation.theme.primary500
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.textSecondryColor
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    navToDetails: (Product) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val products = viewModel.products.collectAsLazyPagingItems()





    Scaffold(
        topBar = { ProductScreenTopAppBar(scrollBehavior) },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        if (products.loadState.refresh is LoadState.Loading)
            LoadingState()

        if (products.loadState.append is LoadState.Error) {
            val message = (products.loadState.append as LoadState.Error).error.message
            message?.let { ErrorState() }

        }

        LazyVerticalGrid(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
        ) {
            items(products.itemCount) { index ->
                products[index]?.let { product ->
                    ProductItem(product = product) {
                        navToDetails(product)
                    }
                }
            }

        }



        if (products.loadState.append is LoadState.Loading)
            Box(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                BallClipRotatePulseProgressIndicator(color = primary500)
            }



    }


}


@Composable
private fun ProductItem(product: Product, onClick: () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val numberOfColumns = 2 // Change this based on your requirement

    val itemWidth = (screenWidth - (numberOfColumns - 1) * 1.dp) / numberOfColumns


    Card(modifier = Modifier
        .clickable { onClick() }
        .fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(0.dp)

    ) {

        Column(
            modifier = Modifier
                .width(itemWidth)
                .height(370.dp),
        ) {
            Box {
                val requestBuilder = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageSizes.indexArray.mediumImageUrl).scale(Scale.FIT)
                    .size(Size.ORIGINAL)

                    .crossfade(true).build()

                AsyncImage(
                    model = requestBuilder,
                    contentDescription = product.name,
                    //contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(PaddingValues(24.dp)),
                    error = painterResource(R.drawable.not_found),

                    )
            }
            Column {
                Text(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(start = 16.dp, end = 8.dp, top = 0.dp),
                    text = product.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = UUID.randomUUID().toString(),
                        tint = skyBlue,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Text(
                        text = "موجود در انبار دیجی کالا",
                        style = MaterialTheme.typography.labelSmall,
                        color = textSecondryColor,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )

                PriceRow(
                    discount = product.discounts,
                    originalPrice = product.price.productPrice,
                )

            }
        }

    }


}








































