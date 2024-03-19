package com.example.digishop.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.digishop.domain.product.model.Product
import com.example.digishop.domain.session.SessionState
import com.example.digishop.presentation.component.DetailScreenTopAppBar
import com.example.digishop.presentation.component.LoadingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    sessionState: SessionState,
    onNavigateUp: () -> Unit,
    navigateToAuthScreen: () -> Unit,
    toMetaScreen: (Product) -> Unit,
    toReviewScreen: (Product) -> Unit,
    toGalleryScreen: (Product) -> Unit,
    toCartScreen:() ->Unit

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state by viewModel.viewState.collectAsStateWithLifecycle()



    state.userMessage?.let { userMessage ->
        val snackbarText = stringResource(userMessage)
        LaunchedEffect(userMessage, snackbarText, viewModel) {
            snackbarHostState.showSnackbar(snackbarText)
            viewModel.userMessageShown()
        }
    }

    if (state.isLoading) {
        LoadingState()
    }


        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DetailScreenTopAppBar(
                  scrollBehavior =   scrollBehavior,
                    onNavigateUp = onNavigateUp ,
                    cartItemsCount = state.cartCount,
                    onNavigateToCart = toCartScreen
                )
            }
        ) { paddingValues ->

            state.product?.let { product ->

                DetailsContent(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.background)
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    state = state,
                    navigateToMetaScreen = toMetaScreen,
                    navigateToReviewScreen = toReviewScreen,
                    navigateToGalleryScreen = toGalleryScreen,
                    onDeleteButton = viewModel::onDeleteButton,
                    onDecrementButton = viewModel::onDecrementButton,
                    onIncrementButton = viewModel::onIncrementButton,
                    onAddButton = {
                        if (sessionState == SessionState.AUTHENTICATED) {

                            viewModel.onAddButton()

                        } else {
                            navigateToAuthScreen()
                        }
                    },
                    onColorSelected = viewModel::onSelectedColor,
                    product = product
                )

            }


        }


}

