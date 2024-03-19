package com.example.digishop.presentation.screens.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digishop.data.remote.util.onFailure
import com.example.digishop.data.remote.util.onSuccess
import com.example.digishop.domain.cart.model.CartItem
import com.example.digishop.domain.cart.model.CartSummery
import com.example.digishop.domain.cart.usecase.GetCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OrderState(
    val isLoading: Boolean = false,
    val cartItems: MutableList<CartItem> = mutableListOf(),
    val cartSummery: CartSummery = CartSummery()

)

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getCartItems: GetCartItemsUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(OrderState())
    val viewState = _viewState.asStateFlow()


    init {
        getCartItems()
    }


    private fun getCartItems() {

        _viewState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            getCartItems.invoke().collect { result ->

                result.onSuccess { cartResult ->

                    _viewState.update {
                        it.copy(
                            cartItems = cartResult.cartItem.toMutableList(),
                            isLoading = false,
                            cartSummery = cartResult.cartSummery
                        )
                    }


                }.onFailure { failure ->
                }

            }
        }

    }
}