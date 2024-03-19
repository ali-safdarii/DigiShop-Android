package com.example.digishop.domain.cart.usecase

import org.junit.Assert.*
import org.junit.Test


/**
 * Data class representing an item in the shopping cart for testing purposes.
 * @property name The name of the item.
 * @property unitPrice The unit price of the item.
 * @property qty The quantity of the item.
 * @property discount The discount applied to the item.
 */
private data class CartItemTest(
    val name: String,
    val unitPrice: Int,
    val qty: Int,
    val discount: Int,
)


/**
 * Data class representing the summary of the shopping cart.
 * @property title The title of the summary.
 * @property itemCount The total number of items in the cart.
 * @property totalPriceWithoutDiscount The total price of items without considering discounts.
 * @property totalDiscount The total discount applied to the items.
 * @property total The overall total price of the items after discounts.
 */
private data class CartSummeryTest(
    val title: String = "",
    val itemCount: Int = 0,
    val totalPriceWithoutDiscount: Int = 0,
    val totalDiscount: Int = 0,
    val total: Int = 0,
)


/**
 * Calculates the total price summary of items in the shopping cart.
 * @param cartItems The list of [CartItemTest] representing items in the cart.
 * @return A [CartSummaryTest] object containing the total price summary.
 */
private fun calculateTotalPriceTest(cartItems: List<CartItemTest>): CartSummeryTest {
    val totalCount = cartItems.size
    val totalPriceWithoutDiscount = cartItems.sumOf { it.unitPrice * it.qty }
    val totalDiscount = cartItems.sumOf { it.qty * it.discount }
    val overallTotal = totalPriceWithoutDiscount - totalDiscount

    return CartSummeryTest(
        itemCount = totalCount,
        totalPriceWithoutDiscount = totalPriceWithoutDiscount,
        totalDiscount = totalDiscount,
        total = overallTotal,
    )
}

class GetCartItemsUseCaseTest {


    /**
     * Test case to verify the calculation of total price with discounts applied.
     */
    @Test
    fun `success calculateTotalPriceTest with discount`() {
         val cartItems = listOf(
            CartItemTest(name = "Product1", unitPrice = 20, qty = 2, discount = 10),
            CartItemTest(name = "Product2", unitPrice = 30, qty = 2, discount = 20),
            CartItemTest(name = "Product3", unitPrice = 40, qty = 1, discount = 30)
        )

        val result = calculateTotalPriceTest(cartItems)


        assertEquals(140, result.totalPriceWithoutDiscount) // final price without calculate discounts
        assertEquals(90, result.totalDiscount) // calcualte sum of all discounts --| qty * discount|--
        assertEquals(50, result.total) // apply discount to the final price  --| 140 - 90 = 50 |--
    }


    /**
     * Test case to verify the calculation of total price without discounts.
     */
    @Test
    fun ` success calculateTotalPriceTest without discount`() {
        val cartItems = listOf(
            CartItemTest(name = "Product1", unitPrice = 20, qty = 2, discount = 0),
            CartItemTest(name = "Product2", unitPrice = 30, qty = 2, discount = 0),
            CartItemTest(name = "Product3", unitPrice = 40, qty = 1, discount = 0)
        )

        val result = calculateTotalPriceTest(cartItems)


        assertEquals(140, result.totalPriceWithoutDiscount)
        assertEquals(140, result.total)
    }


}