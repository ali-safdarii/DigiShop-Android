package com.example.digishop.presentation.screens.cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.digishop.R
import com.example.digishop.presentation.theme.white

@Composable
fun EmptyCart(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = R.drawable.undraw_empty_cart_co,
            contentDescription = "Not_found_404",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
                .padding(16.dp)
        )

        Text(
            text = stringResource(R.string.empty_cart_message),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 8.dp, top = 0.dp)
        )

    }
}