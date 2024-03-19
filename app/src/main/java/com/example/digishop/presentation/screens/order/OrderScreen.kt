

package com.example.digishop.presentation.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.digishop.R
import com.example.digishop.presentation.component.LoadingState
import com.example.digishop.presentation.component.OrderScreenTopAppBar
import com.example.digishop.presentation.theme.white
import com.example.pickyshop.presentation.screens.order.OrderContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    onNavigateUp: () -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    /*orderDone var simulatie The User Order Is Complete and showing lottie animation When Click The Button  */
    var orderDone by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.order_done))


    if (!orderDone) {
        if (state.isLoading) {
            LoadingState()
        } else {
            Scaffold(
                topBar = {
                    OrderScreenTopAppBar(
                        behavior = scrollBehavior,
                        onNavigateUp = onNavigateUp
                    )
                }

            ) { paddingValues ->

                OrderContent(
                    modifier = Modifier.padding(paddingValues),
                    cartItemList = state.cartItems,
                    cartSummery = state.cartSummery,
                    onClick = { orderDone = true }
                )
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .background(white),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition,
                modifier = Modifier.padding(PaddingValues(24.dp)),
            )

            Text(
                text = "کاربر گرامی سفارش شما با موفقیت ثبت شد",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }


}





