package com.example.digishop.presentation.screens.home


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.digishop.data.remote.responses.product_response.Category
import com.example.digishop.domain.product.model.Product

import com.example.digishop.presentation.component.ErrorState
import com.example.digishop.presentation.component.LoadingState
import timber.log.Timber


@Composable
fun MainScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigatToDetailsScreen: (Product) -> Unit,
    navigatToCategoryScreen: (Category) -> Unit,
    navigateToProductScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
) {
    val mainState by viewModel.viewState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    when (val state = mainState) {
        is HomeState.Loading -> LoadingState()
        is HomeState.Success ->

            MainContent(
                state,
                onCategoryClick = navigatToCategoryScreen,
                onProductClick = navigatToDetailsScreen,
                toProductScreen = navigateToProductScreen,
                toSearchScreen = navigateToSearchScreen,
                snackbarHostState = snackbarHostState,
            )

        is HomeState.Error -> {

            ErrorState()
            state.customErrorMessage.let {
                Timber.e(it.message)
                it.userMessage?.let { userMessage ->
                    val snackbarText = stringResource(userMessage)
                    LaunchedEffect(userMessage, snackbarText, viewModel) {
                        snackbarHostState.showSnackbar(snackbarText)
                        viewModel.userMessageShown()
                    }
                }
            }

        }
    }
}

