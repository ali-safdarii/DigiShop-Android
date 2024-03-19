package com.example.digishop.presentation.screens.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digishop.data.remote.util.onFailure
import com.example.digishop.data.remote.util.onSuccess
import com.example.digishop.domain.category.usecase.ProductByCategoryIdUseCase
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.navigation.ScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CategoryUiState(
    val isLoading: Boolean = false,
    val productList: List<Product> = emptyList()
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val productByCategoryId: ProductByCategoryIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val catId: Int = checkNotNull(savedStateHandle[ScreenArgs.CATEGORY_ID_ARG])

    private val _viewState = MutableStateFlow(CategoryUiState())
    val viewState = _viewState.asStateFlow()


    init {
        loadproductByCategoryId()
    }

    private fun loadproductByCategoryId() {
        _viewState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            productByCategoryId.invoke(catId).collect { result ->
                result.onSuccess { productList ->
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            productList = productList
                        )
                    }
                }.onFailure {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }

            }

        }
    }
}