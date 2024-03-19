package com.example.pickyshop.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digishop.data.remote.util.onFailure
import com.example.digishop.data.remote.util.onSuccess
import com.example.digishop.domain.cart.model.CartItem
import com.example.digishop.domain.cart.model.CartSummery
import com.example.digishop.domain.cart.usecase.DeleteCartUseCase
import com.example.digishop.domain.cart.usecase.GetCartItemsUseCase
import com.example.digishop.domain.cart.usecase.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class CartState(
    val userMessage: Int? = null,
    val qtyLoading: Boolean = false,
    val screenLoading: Boolean = false,
    val cartItems: MutableList<CartItem> = mutableListOf(),
    val totalPrice: Int = 0,
    val qty: Int = -1,
    val cartSummery: CartSummery = CartSummery()
)


@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItems: GetCartItemsUseCase,
    private val updateQuantity: UpdateCartQuantityUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(CartState())
    val viewState = _viewState.asStateFlow()


    init {
        viewModelScope.launch {
            getCartItems()
        }
    }



    fun onIncrement(item: CartItem) {
        val cartItemList = _viewState.value.cartItems
        cartItemList.find { it.cartItemId == item.cartItemId }?.let { cartItem ->
            cartItem.quantity += 1

            viewModelScope.launch {
                updateQty(
                    cartItem = cartItem,
                    quantity = cartItem.quantity,
                    finalPrice = cartItem.finalPrice,
                )
            }


        }

    }


    fun onDecrement(item: CartItem) {
        val cartItemList = _viewState.value.cartItems

        cartItemList.find { it.cartItemId == item.cartItemId }?.let { cartItem ->

            cartItem.quantity -= 1


            if (cartItem.quantity >= 1) {
                viewModelScope.launch {
                    updateQty(
                        cartItem = cartItem,
                        quantity = cartItem.quantity,
                        finalPrice = cartItem.finalPrice,
                    )
                }
            }


        }
    }


     fun deleteCartItem(item: CartItem) {
        val cartItemList = _viewState.value.cartItems

        cartItemList.find { it.cartItemId == item.cartItemId }?.let { cartItem ->
            viewModelScope.launch {
                deleteFromCart(cartItem)
            }

        }
    }


    private suspend fun updateQty(cartItem: CartItem, quantity: Int, finalPrice: Int) {

        cartItem.isLoading = true

        _viewState.update { currentState ->
            currentState.copy(
                qtyLoading = cartItem.isLoading,
                screenLoading = false
            )
        }
        updateQuantity.invoke(
            productId = cartItem.product.id,
            colorId = cartItem.colorId,
            quantity = quantity,
            finalPrice = finalPrice
        ).onSuccess {
            cartItem.isLoading = false
            cartItem.quantity = quantity
            cartItem.primaryDiscount = cartItem.productDiscount * cartItem.quantity
            cartItem.finalPrice = cartItem.unitPrice * cartItem.quantity

            val updatedTotalPriceWithoutDiscount = _viewState.value.cartItems.sumOf {
                it.unitPrice * it.quantity
            }

            val updatedTotalDiscount =
                _viewState.value.cartItems.sumOf { it.quantity * it.productDiscount }


            val updatedOverallTotal = updatedTotalPriceWithoutDiscount - updatedTotalDiscount



            _viewState.update { currentState ->
                currentState.copy(
                    qty = cartItem.quantity,
                    qtyLoading = cartItem.isLoading,
                    screenLoading = false,
                    cartSummery = currentState.cartSummery.copy(
                        totalPriceWithoutDiscount = updatedTotalPriceWithoutDiscount,
                        totalDiscount = updatedTotalDiscount,
                        total = updatedOverallTotal
                    )
                )
            }

        }.onFailure { failure ->
            failure.errorTypeMessage.stringRes?.let { showMessage(it) }
        }


    }

    private suspend fun deleteFromCart(cartItem: CartItem) {


        deleteCartUseCase.invoke(colorId = cartItem.colorId, productId = cartItem.product.id)
            .onSuccess {
                val updatedCartItems = _viewState.value.cartItems.toMutableList()
                updatedCartItems.remove(cartItem)


                val updatedTotalPriceWithoutDiscount = updatedCartItems.sumOf {
                    it.unitPrice * it.quantity
                }

                val updatedTotalDiscount = updatedCartItems.sumOf {
                    it.quantity * it.productDiscount
                }

                val updatedOverallTotal = updatedTotalPriceWithoutDiscount - updatedTotalDiscount


                _viewState.update {
                    it.copy(
                        cartItems = updatedCartItems,
                        cartSummery = it.cartSummery.copy(
                            itemCount = updatedCartItems.size,
                            totalPriceWithoutDiscount = updatedTotalPriceWithoutDiscount,
                            totalDiscount = updatedTotalDiscount,
                            total = updatedOverallTotal
                        )
                    )
                }
            }.onFailure { failure ->
                failure.errorTypeMessage.stringRes?.let { showMessage(it) }
            }

    }


    private suspend fun getCartItems() {

        _viewState.update {
            it.copy(screenLoading = true)
        }

        getCartItems.invoke().collect { result ->

            result.onSuccess { cartResult ->

                _viewState.update {
                    it.copy(
                        cartItems = cartResult.cartItem.toMutableList(),
                        cartSummery = cartResult.cartSummery,
                        screenLoading = false
                    )
                }

            }.onFailure { failure ->
                _viewState.update {
                    it.copy(screenLoading = false)
                }
                failure.errorTypeMessage.stringRes?.let { showMessage(it) }
                failure.errorTypeMessage.localMessage?.let { Timber.e(it) }
            }

        }
    }


    fun userMessageShown() {
        _viewState.update {
            it.copy(userMessage = null)
        }
    }


    private fun showMessage(message: Int) {
        _viewState.update {
            it.copy(userMessage = message)
        }
    }


}

