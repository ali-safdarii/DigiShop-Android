package com.example.digishop.presentation.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digishop.data.remote.util.onFailure
import com.example.digishop.data.remote.util.onSuccess
import com.example.digishop.domain.cart.usecase.AddCartUseCase
import com.example.digishop.domain.cart.usecase.DeleteCartUseCase
import com.example.digishop.domain.cart.usecase.ItemsCountUseCase
import com.example.digishop.domain.cart.usecase.ProductInCartUseCase
import com.example.digishop.domain.cart.usecase.UpdateCartQuantityUseCase
import com.example.digishop.domain.product.model.Product
import com.example.digishop.domain.product.model.ProductColor
import com.example.digishop.domain.product.usecase.GetProductByIdUseCase
import com.example.digishop.presentation.navigation.ScreenArgs
import com.example.digishop.presentation.screens.details.sections.QtyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class DetailsState(
    val userMessage: Int? = null,
    val isLoading: Boolean = false,
    val product: Product? = null,
    var cartCount: Int = 0,
    val qty: Int = 1,
    val buttonState: ButtonState = ButtonState.ADD_TO_CART,
    val qtyState: QtyState = QtyState.INITIAL,
    val colorId: Int = 0,
    val totalPrice: Int = 0,
    val selectedColor: ProductColor? = null,
    val colors: MutableList<ProductColor> = mutableListOf(),
    )

enum class ButtonState {
    ADD_TO_CART, UPDATE_QUANTITY
}


@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addCartUseCase: AddCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val updateQuantity: UpdateCartQuantityUseCase,
    private val productInCartUseCase: ProductInCartUseCase,
    private val itemsCountUseCase: ItemsCountUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,

    ) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle[ScreenArgs.PRODUCT_ID_ARG])


    private val _viewState = MutableStateFlow(DetailsState())
    val viewState = _viewState.asStateFlow()


    init {
        initial()
    }

    private fun initial() {
        viewModelScope.launch {
            loadproductById()
            cartItemsCount()

            _viewState.value.product?.let { product ->

                val selectedColor = product.colors.find {
                    it.id == product.defualtColorId
                } ?: product.colors.firstOrNull()


                val colorPrice = selectedColor!!.price
                val totalPrice = product.price.totalPrice(colorPrice)

                _viewState.update {
                    it.copy(
                        selectedColor = selectedColor,
                        colorId = selectedColor.id,
                        totalPrice = totalPrice
                    )
                }

                existProduct(productId = productId, colorId = _viewState.value.colorId)

                Timber.d("${product.defualtColorId}")

            }
        }
    }


    private fun existProduct(productId: Int, colorId: Int) {
        _viewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            productInCartUseCase.invoke(productId, colorId)
                .collect { result ->

                    result.onSuccess { response ->
                        _viewState.update { it.copy(isLoading = false) }
                        val isProductInCart = response.isExsit
                        val buttonState =
                            if (isProductInCart) ButtonState.UPDATE_QUANTITY else ButtonState.ADD_TO_CART

                        val qtyState =
                            if (isProductInCart && response.qty > 1) QtyState.DECREASE else QtyState.DELETE_FROM_CART


                        _viewState.update {
                            it.copy(
                                buttonState = buttonState,
                                qtyState = qtyState,
                                qty = response.qty,
                                //  totalPrice = _viewState.value.totalPrice * response.qty

                            )
                        }

                    }.onFailure { failure ->
                        _viewState.update { it.copy(isLoading = false) }
                        failure.errorTypeMessage.stringRes?.let { showMessage(it) }
                    }


                }
        }

    }

    private fun saveIntoCart(colorId: Int, finalPrcie: Int, qty: Int) {

        viewModelScope.launch {

            addCartUseCase.invoke(
                productId = productId,
                colorId = colorId,
                finalPrice = finalPrcie,
                qty = qty
            ).onSuccess {
                val updatedCartCount = viewState.value.cartCount.plus(1)
                _viewState.update {
                    it.copy(
                        cartCount = updatedCartCount,
                        buttonState = ButtonState.UPDATE_QUANTITY,
                        qty = qty,
                    )
                }

            }.onFailure { failure ->
                failure.errorTypeMessage.stringRes?.let { showMessage(it) }
            }


        }

    }

    private fun updateQty(colorId: Int, newQty: Int, finalPrcie: Int) {
        _viewState.update {
            it.copy(
                qty = newQty,
                qtyState = if (newQty == 1) QtyState.DELETE_FROM_CART else QtyState.DECREASE,
                isLoading = true
            )
        }

        viewModelScope.launch {


            updateQuantity.invoke(
                productId = productId,
                colorId = colorId,
                quantity = newQty,
                finalPrice = finalPrcie
            ).onSuccess {
                _viewState.update {
                    it.copy(
                        isLoading = false
                    )
                }

            }.onFailure { failure ->
                failure.errorTypeMessage.stringRes?.let { showMessage(it) }
            }

        }

    }

    private fun deleteFromCart(colorId: Int) {

        viewModelScope.launch {
            deleteCartUseCase
                .invoke(
                    colorId = colorId,
                    productId = productId
                ).onSuccess {
                    val updatedCartCount = viewState.value.cartCount.minus(1)
                    _viewState.update {
                        it.copy(
                            buttonState = ButtonState.ADD_TO_CART,
                            cartCount = updatedCartCount
                        )
                    }

                }.onFailure { failure ->
                    failure.errorTypeMessage.stringRes?.let { showMessage(it) }
                }


        }
    }

    private suspend fun loadproductById() {
        _viewState.update {
            it.copy(
                isLoading = true,

                )
        }

        getProductByIdUseCase.invoke(productId).collect { result ->

            result.onSuccess { product ->
                _viewState.update {
                    it.copy(
                        product = product,
                        isLoading = false,
                        colors = product.colors.toMutableList(),
                    )
                }

            }.onFailure { failure ->
                failure.errorTypeMessage.stringRes?.let { showMessage(it) }
            }

        }


    }

    private suspend fun cartItemsCount() {

        itemsCountUseCase.invoke().onSuccess { data ->
            _viewState.update {
                it.copy(
                    cartCount = data.itemsCount,
                )
            }
        }.onFailure { }


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


    fun onSelectedColor(item: ProductColor) {
        val product = _viewState.value.product!!
        val colorPrice = item.price
        val totalPrice = product.price.totalPrice(colorPrice)

        existProduct(productId = product.id, colorId = item.id)


        _viewState.update {
            it.copy(
                selectedColor = item,
                colorId = item.id,
                totalPrice = totalPrice,
            )
        }
    }


    fun onAddButton() {
        val colorId = _viewState.value.selectedColor?.id ?: 0
        val finalPrice = _viewState.value.totalPrice
        val qty = 1
        saveIntoCart(colorId = colorId, finalPrcie = finalPrice, qty = qty)
    }

    fun onDeleteButton() {
        val colorId = _viewState.value.colorId
        deleteFromCart(colorId)
    }

    fun onDecrementButton() {
        val colorId = _viewState.value.colorId
        val qty = _viewState.value.qty - 1
        val newQty = maxOf(1, qty)
        val finalPrice = _viewState.value.product?.let {
            it.price.totalPrice(_viewState.value.selectedColor?.price ?: 0) * newQty
        } ?: 0
        updateQty(colorId = colorId, newQty = newQty, finalPrcie = finalPrice)

        _viewState.update {
            it.copy(
                totalPrice = finalPrice
            )
        }
    }

    fun onIncrementButton() {
        val colorId = _viewState.value.colorId
        val qty = _viewState.value.qty + 1
        val finalPrice = _viewState.value.product?.let {
            it.price.totalPrice(_viewState.value.selectedColor?.price ?: 0) * qty
        } ?: 0
        updateQty(colorId = colorId, newQty = qty, finalPrcie = finalPrice)

        _viewState.update {
            it.copy(
                totalPrice = finalPrice
            )
        }
    }




}



















