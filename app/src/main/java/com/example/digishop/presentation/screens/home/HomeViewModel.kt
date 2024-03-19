package com.example.digishop.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digishop.data.remote.responses.amazing_products.response.AmazingData
import com.example.digishop.data.remote.responses.banners.BannerItem
import com.example.digishop.data.remote.responses.product_response.Category
import com.example.digishop.data.remote.util.AsyncResult as Result
import com.example.digishop.domain.banners.BannerUseCase
import com.example.digishop.domain.category.usecase.CategoryUseCase
import com.example.digishop.domain.product.model.Product
import com.example.digishop.domain.product.usecase.AmazingProductUseCase
import com.example.digishop.domain.product.usecase.ProductSummeryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import timber.log.Timber

/*
@Immutable
sealed interface SummeryProductUiState {
    data class Success(val products: List<Product>) : SummeryProductUiState
    data class Error(val message: String?) : SummeryProductUiState
//    object Loading : SummeryProductUiState
}

@Immutable
sealed interface AmazingProductUiState {
    data class Success(val amazingProducts: List<AmazingData>) : AmazingProductUiState
    data class Error(val message: String?) : AmazingProductUiState
    //   object Loading : AmazingProductUiState
}


@Immutable
sealed interface BannerUiState {
    data class Success(val bannerItem: List<BannerItem>) : BannerUiState
    object Error : BannerUiState
    //   object Loading : BannerUiState
}


@Immutable
sealed interface CategoryUiState {
    data class Success(val categries: List<Category>) : CategoryUiState
    object Error : CategoryUiState
    // object Loading : CategoryUiState
}


data class HomeUiState(
    val summeryProducts: SummeryProductUiState,
    val amazingProducts: AmazingProductUiState,
    val bannerItem: BannerUiState,
    val categories: CategoryUiState,
    val isRefreshing: Boolean,
    val isError: Boolean
)*/



data class HomeCustomMessage(
    val message: String? = null, val userMessage: Int? = null
)

sealed class HomeState {
    object Loading : HomeState()
    data class Success(
        val summeryProducts: List<Product>,
        val amazingProducts: List<AmazingData>,
        val bannerItem: List<BannerItem>,
        val categries: List<Category>
    ) : HomeState()

    data class Error(val customErrorMessage: HomeCustomMessage) : HomeState()
}


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val banners: BannerUseCase,
    private val amazingProduct: AmazingProductUseCase,
    private val categories: CategoryUseCase,
    private val summeries: ProductSummeryUseCase

) : ViewModel() {


    private val _viewState = MutableStateFlow<HomeState>(HomeState.Loading)
    val viewState: StateFlow<HomeState> = _viewState.asStateFlow()


    init {


        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            val summeryOfProductsFlow = summeries.invoke()
            val amazingProductsFLow = amazingProduct.invoke()
            val categoriesFlow = categories.invoke()
            val bannersFLow = banners.invoke()

            combine(
                summeryOfProductsFlow,
                amazingProductsFLow,
                bannersFLow,
                categoriesFlow
            ) { summeryResult, amazingProductsResult, bannersResult, categoriesResult ->

                if (summeryResult is Result.Failure) {
                    val snackBarMessage = summeryResult.error.errorTypeMessage.stringRes
                    val message = summeryResult.error.errorTypeMessage.localMessage
                        ?: "Failure summeryResult "

                    snackBarMessage?.let {
                        showMessage(it)
                    }
                    return@combine HomeState.Error(
                        HomeCustomMessage(
                            message = message,
                            userMessage = snackBarMessage
                        )
                    )
                }



                if (amazingProductsResult is Result.Failure) {
                    val snackBarMessage = amazingProductsResult.error.errorTypeMessage.stringRes
                    val message = amazingProductsResult.error.errorTypeMessage.localMessage
                        ?: "Failure amazingProductsResult "


                    snackBarMessage?.let {
                        showMessage(it)
                    }
                    return@combine HomeState.Error(
                        HomeCustomMessage(
                            message = message,
                            userMessage = snackBarMessage
                        )
                    )
                }


                if (bannersResult is Result.Failure) {
                    val snackBarMessage = bannersResult.error.errorTypeMessage.stringRes
                    val message =
                        bannersResult.error.errorTypeMessage.localMessage ?: "Failure bannersResult"

                    snackBarMessage?.let {
                        showMessage(it)
                    }

                    return@combine HomeState.Error(
                        HomeCustomMessage(
                            message = message,
                            userMessage = snackBarMessage
                        )
                    )
                }


                if (categoriesResult is Result.Failure) {
                    val snackBarMessage = categoriesResult.error.errorTypeMessage.stringRes
                    val message = categoriesResult.error.errorTypeMessage.localMessage
                        ?: "Failure categoriesResult"

                    snackBarMessage?.let {
                        showMessage(it)
                    }

                    return@combine HomeState.Error(
                        HomeCustomMessage(
                            message = message,
                            userMessage = snackBarMessage
                        )
                    )
                }

                val summeryProduct = (summeryResult as Result.Success).data
                val banners = (bannersResult as Result.Success).data

                val amazingProducts = (amazingProductsResult as Result.Success).data
                val categories = (categoriesResult as Result.Success).data


                HomeState.Success(
                    summeryProducts = summeryProduct,
                    amazingProducts = amazingProducts,
                    bannerItem = banners,
                    categries = categories.take(9)
                )

            }.catch { throwable ->
                Timber.e(throwable)
            }.collect {
                _viewState.value = it
            }
        }
    }


    fun userMessageShown() {
        _viewState.value = HomeState.Error(HomeCustomMessage(userMessage = null))
    }


    private fun showMessage(message: Int) {
        _viewState.value = HomeState.Error(HomeCustomMessage(userMessage = message))
    }
}