package com.example.digishop.presentation.screens.home.see_more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.digishop.domain.product.model.Product
import com.example.digishop.domain.product.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(repository: ProductRepository) : ViewModel() {

    val products: Flow<PagingData<Product>> =
        repository.productPagingData().cachedIn(viewModelScope)


}