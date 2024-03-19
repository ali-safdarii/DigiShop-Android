package com.example.digishop.presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.digishop.R
import com.example.digishop.presentation.entity.OnBoardingItems
import com.example.digishop.presentation.entity.onboardPages
import com.example.digishop.presentation.theme.white
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navToHome: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {

    val items = onboardPages
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { items.size })
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    val buttonsState = remember {
        derivedStateOf {
            if (pagerState.currentPage == 2)
                "پس امتحان کن"
            else
                "ورق بزن"
        }
    }

    if (state.appEntrySuccess)
        navToHome()
    else{
        Column(
            modifier = Modifier
                .background(white)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
            ) { page ->
                OnBoardingItem(items = items[page])
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PagerIndicator(pagesSize = items.size, selectedPage = pagerState.currentPage)

                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 2) {
                                viewModel.saveAppEntry()
                            } else {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    },
                    shape = RoundedCornerShape(20),
                ) {

                    Text(
                        text = buttonsState.value,
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }

        }
    }
}


@Composable
private fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int,
    indicatorSize: Dp = 10.dp,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Color(0XFFF8E2E7),
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        repeat(times = pagesSize) { page ->
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}


@Composable
private fun OnBoardingItem(items: OnBoardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {


        Image(
            painter = rememberAsyncImagePainter(model = items.image),
            contentDescription = items.description,
            modifier = Modifier
                .weight(1.5f)
                .padding(PaddingValues(16.dp))

        )


        Column(
            modifier = Modifier
                .weight(1f)
                .padding(PaddingValues(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.lorem_ipsum),
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                // fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
            )

            Text(
                text = stringResource(id = R.string.lorem_ipsum),
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp),
                letterSpacing = 1.sp,
            )
        }
    }
}