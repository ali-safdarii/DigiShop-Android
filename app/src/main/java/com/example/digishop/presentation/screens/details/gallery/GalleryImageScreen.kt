package com.example.pickyshop.presentation.screens.details.gallery

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.digishop.data.remote.responses.product_response.Gallery
import com.example.digishop.presentation.component.CachedPagerImage
import com.example.digishop.presentation.component.LoadingState
import com.example.digishop.presentation.component.bottomBorder
import com.example.digishop.presentation.screens.details.DetailsViewModel
import com.example.digishop.presentation.theme.skyBlue
import com.example.digishop.presentation.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryImageScreen(
    onBackPressed:() ->Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val galleryList = state.product?.galleries ?: emptyList()
    val pageCount = galleryList.size
    val pagerState = rememberPagerState(pageCount = {
        pageCount
    })
    val listState = rememberLazyListState()

    if (state.isLoading){
        LoadingState()
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(white),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            IconButton(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Start),
                onClick = {
                    onBackPressed()
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close",
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(.7f)
            ) { page ->
                CachedPagerImage(
                    page,
                    imageUrl = galleryList[page].imageSizes.indexArray.mediumImageUrl,
                    content = {
                        LargeGalleryImageItem(imageUri = it)
                    })
            }

            Text(
                "${pagerState.currentPage + 1} / $pageCount",
                fontSize = 16.sp,
                modifier = Modifier.weight(.1f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(.2f), contentAlignment = Alignment.Center) {
                SmallPreviewImage(pagerState, listState,galleryList)
            }
        }
    }



}


@Composable
private fun LargeGalleryImageItem(imageUri: Uri) {

    // Display the large image
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)

    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(8.dp))

        )
    }

}


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
private fun SmallPreviewImage(pagerState: PagerState, listState: LazyListState,galleries:List<Gallery>) {
    var selectedIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage

    // Scroll to the current page when the current page changes
    LaunchedEffect(currentPage) {
        listState.animateScrollToItem(
            index = currentPage
        )
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // Add a spacer to the left
        Spacer(modifier = Modifier.size(25.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(0.2f)
                .align(Alignment.CenterVertically)
        ) {
            LazyRow(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                state = listState,
            ) {
                // Show each small preview image in a SmallPreviewItem composable
                itemsIndexed(galleries) { index, item ->
                    SmallPreviewItem(
                        image = item.imageSizes.indexArray.mediumImageUrl,
                        onClick = {
                            selectedIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page = selectedIndex,
                                    animationSpec = tween(
                                        durationMillis = 500, // Adjust the duration as needed
                                        easing = LinearEasing
                                    )
                                )
                            }
                        },
                        selected = currentPage == index,
                    )

                }
            }
        }
    }
}

@Composable
private fun SmallPreviewItem(
    image: String,
    onClick: () -> Unit,
    selected: Boolean = false
) {

    val color = if (selected) skyBlue else Color.Transparent
    val updatedSelected = rememberUpdatedState(selected)


    Box(modifier = Modifier
        .padding(4.dp)
        .bottomBorder(3.dp, color)
        .wrapContentHeight()
        .fillMaxWidth()
        .clickable {
            onClick()
        }) {

        AsyncImage(
            model = image,
            contentDescription = "",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(100.dp)
                .padding(12.dp)
                .padding(6.dp)
        )

    }

}





























