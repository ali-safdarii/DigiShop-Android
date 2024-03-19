package com.example.digishop.presentation.screens.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.digishop.R
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.LoadingState
import com.example.digishop.presentation.component.PriceRow
import com.example.digishop.presentation.theme.backgroundColor
import com.example.digishop.presentation.theme.dividerColor
import com.example.digishop.presentation.theme.textSecondryColor
import com.example.digishop.presentation.theme.white
import timber.log.Timber

@Composable
fun SearchLoadingScreen(
    initialLoadErrorMessage: String?,
    onSearchValueChanged: (String) -> Unit,
    onSearch: () -> Unit,
    searchText: String,


    ) {

    Column {

        val errorMessage = initialLoadErrorMessage ?: ""

        if (errorMessage == "HTTP 404 Not Found") {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(white),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                AsyncImage(
                    model = R.drawable.undraw_no_data,
                    contentDescription = "Not_found_404",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(250.dp)
                        .padding(16.dp)
                        .padding(16.dp)
                )

                Text(
                    text = "نتیجه ایی یافت نشد...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 0.dp)
                )
                /*  Row(
                      modifier = Modifier
                          .padding(PaddingValues(8.dp))
                  ) {
                      Icon(
                          imageVector = Icons.Filled.Warning,
                          contentDescription = "Warning",
                          tint = yellow
                      )

                      Text(
                          text = "نتیجه ایی یافت نشد...",
                          style = MaterialTheme.typography.bodyLarge,
                          modifier = Modifier
                              .padding(start = 8.dp, top = 0.dp)
                      )

                  }*/
            }
        } else {
            SearchTextField(
                searchText = searchText,
                onValueChanged = onSearchValueChanged,
                onSearchWithText = null,
                onSearch = onSearch
            )
        }
    }
}

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), navToDetails: (Product) -> Unit,) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = white
    ) { paddingValues ->

        val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
        val searchText = viewModel.searchText.collectAsStateWithLifecycle().value
        val loadState = searchResults.loadState


        val showInitialLoadingOrErrorScreen = loadState.refresh !is LoadState.NotLoading


        val initialLoadErrorMessage = if (loadState.refresh is LoadState.Error)
            (loadState.refresh as LoadState.Error).error.message
        else
            null

        val notFoundSearchResult = initialLoadErrorMessage == "HTTP 404 Not Found"

        val appendErrorMessage = if (loadState.append is LoadState.Error)
            (loadState.append as LoadState.Error).error.message
        else
            null



        if (!notFoundSearchResult)
            initialLoadErrorMessage?.let { message ->
                val snackbarText = stringResource(id = R.string.err_unknown)
                LaunchedEffect(message, snackbarText) {
                    snackbarHostState.showSnackbar(snackbarText)
                    Timber.e(message)
                }
            }




        AnimatedContent(
            targetState = showInitialLoadingOrErrorScreen, label = "",
            transitionSpec = {
                slideInHorizontally { it } togetherWith slideOutHorizontally { -it } + fadeOut()
            }
        ) { loadingInitialFetch ->


            if (loadingInitialFetch) {
                SearchLoadingScreen(
                    initialLoadErrorMessage = initialLoadErrorMessage,
                    onSearchValueChanged = viewModel::onSearchValueChanged,
                    onSearch = viewModel::onSearch,
                    searchText = searchText
                )
            } else {
                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        SearchTextField(
                            searchText = searchText,
                            onValueChanged = viewModel::onSearchValueChanged,
                            onSearchWithText = null,
                            onSearch = viewModel::onSearch
                        )


                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(backgroundColor)
                                .height(8.dp)
                        )
                    }


                    // Display search results
                    items(searchResults.itemCount) { index ->
                        searchResults[index]?.let { product ->
                            SearchItem(product = product,onItemClick = { navToDetails(product) })
                        }
                    }


                    if (searchResults.loadState.append is LoadState.Loading) {
                        item {
                            LoadingState()
                            Text(text = "لطفا کمی صبر کنید ...")
                        }
                    }

                    appendErrorMessage?.let {
                        item {
                            PagingAppendErrorItem(appendErrorMessage) { searchResults.retry() }
                        }
                    }
                }
            }

        }
    }


}


@Composable
private fun SearchItem(product: Product, onItemClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onItemClick(product) }
            .padding(PaddingValues(4.dp))
            .fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(8.dp)),
        ) {

            val requestBuilder = ImageRequest.Builder(LocalContext.current)
                .data(product.imageSizes.indexArray.mediumImageUrl)
                .scale(Scale.FIT)
                .crossfade(true)
                .build()

            AsyncImage(
                model = requestBuilder,
                contentDescription = product.id.toString(),
                modifier = Modifier
                    // .padding(8.dp)
                    .size(120.dp),
                contentScale = ContentScale.Inside,
                error = painterResource(R.drawable.not_found),
            )


            Column {

                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 8.dp, top = 4.dp),
                    text = product.name,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.End)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .align(Alignment.CenterVertically),
                        text = "4.5",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textSecondryColor
                    )
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.StarRate,
                        contentDescription = "star_rate",
                        tint = Color(0xFFFFC416),
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )

                PriceRow(
                    discount = product.discounts,
                    originalPrice = product.price.productPrice,
                )

            }


        }

        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(dividerColor)
        )


    }

}




























