package com.example.digishop.presentation.screens.details.review

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digishop.presentation.screens.details.DetailsViewModel

@Composable
fun ReviewScreen(viewModel: DetailsViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsState()

    state.product?.let { product ->
        Column {
            Text(text = product.toString())
        }
    }

}