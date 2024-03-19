package com.example.digishop.presentation.screens.category

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.digishop.R
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.CategoryByIdTopAppBar
import com.example.digishop.presentation.component.LoadingState
import com.example.digishop.presentation.component.PriceRow
import com.example.digishop.presentation.component.bottomBorder
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import com.example.digishop.utils.Dimension


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryByIdScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    navToDetails: (Product) -> Unit,
) {

    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    if (state.isLoading) {
        LoadingState()
    }

    val products = state.productList

    Scaffold(topBar = {
        CategoryByIdTopAppBar(
            scrollBehavior = scrollBehavior,
            title = stringResource(R.string.category)
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            items(
                items = products,
                key = { product ->
                    product.id
                }
            ) { product ->

                CategoryItem(product, onItemClick = { navToDetails(product) })

            }

        }
    }


}

@Composable
private fun CategoryItem(product: Product, onItemClick: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(product) }
            .fillMaxWidth()
            .background(white)
            .bottomBorder(1.dp, dividerColor)
            .padding(PaddingValues(24.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.weight(.3f),
            contentAlignment = Alignment.Center
        ) {
            val requestBuilder = ImageRequest.Builder(LocalContext.current)
                .data(product.imageSizes.indexArray.mediumImageUrl)
                .crossfade(true)
                .build()

            AsyncImage(
                model = requestBuilder,
                contentDescription = product.name,
                error = painterResource(R.drawable.not_found),
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .weight(0.7f),
            verticalArrangement = Arrangement.Center,

            ) {


            Text(
                product.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.height(50.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = null,
                    modifier = Modifier.size(Dimension.cartIconSize),
                    tint = skyBlue
                )
                Spacer(modifier = Modifier.width(Dimension.sm))
                Text(
                    text = stringResource(R.string.inventory),
                    style = MaterialTheme.typography.labelSmall,
                    color = textSecondryColor
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            PriceRow(
                discount = product.discounts,
                originalPrice = product.price.productPrice,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            )
        }

    }
}


