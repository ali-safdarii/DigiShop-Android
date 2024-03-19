package com.example.digishop.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.digishop.domain.product.model.Product
import com.example.digishop.domain.product.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {



    val searchResults = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val searchText = MutableStateFlow("")

    fun onSearchValueChanged(newText: String) {
        searchText.value = newText
    }

    fun onSearch() {
        getProducts(searchText.value)
    }






    private fun getProducts(searchText: String) {
        viewModelScope.launch {
            productRepository.searchProduct(searchText)
                .cachedIn(viewModelScope)
                .catch {

                }.collect {
                searchResults.value = it
            }
        }
    }

}