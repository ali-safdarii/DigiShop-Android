package com.example.digishop.presentation.screens.home.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.digishop.R
import com.example.digishop.data.remote.responses.banners.BannerItem
import com.example.digishop.presentation.component.CachedPagerImage
import com.example.digishop.presentation.component.CustomHorizontalIndicator
import com.example.digishop.utils.AdPosition
import com.example.digishop.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdsPager(banners: List<BannerItem>) {

    val filteredSliders =
        banners.filter { it.position == Utils.bannerPositions(AdPosition.SlideShow) }
    val pageCount = filteredSliders.size
    val pagerState = rememberPagerState(pageCount = {
        pageCount
    })

    // Coroutine scope for handling auto-scroll
    val coroutineScope = rememberCoroutineScope()

    // Launch coroutine to handle auto-scroll
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                // Delay for auto-scroll interval (e.g., 3 seconds)
                delay(3000)

                // Move to the next page
                pagerState.animateScrollToPage((pagerState.currentPage + 1) % pageCount)
            }
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) { page ->

            Card(
            ) {
                Box(modifier = Modifier
                    .wrapContentWidth()
                    .height(185.dp)
                    .clickable { }) {
                    CachedPagerImage(page, imageUrl = filteredSliders[page].imageUrl, content = {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(it)
                                .crossfade(true).build(),
                            placeholder = painterResource(R.drawable.loading_image),
                            contentDescription = "${filteredSliders[page].id}",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.align(Alignment.Center)

                        )
                    })


                }
            }
        }

        CustomHorizontalIndicator(
            currentPage = pagerState.currentPage,
            pageCount = pageCount,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 16.dp)
                .wrapContentHeight()
                .fillMaxWidth(),

            )
    }

}
