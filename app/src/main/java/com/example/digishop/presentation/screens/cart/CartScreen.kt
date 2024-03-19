import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.digishop.presentation.theme.gray
import com.example.digishop.presentation.theme.primaryColor
import com.example.digishop.presentation.theme.white
import com.example.pickyshop.presentation.screens.cart.CartViewModel
import com.example.digishop.R
import com.example.digishop.domain.product.model.Product
import com.example.digishop.presentation.component.EmptyState
import com.example.digishop.presentation.component.LoadingState
import com.example.digishop.presentation.screens.cart.component.ShoppingCartList

 enum class TabItem {
    SHOPPING_CART, // سبد خرید
    WISHLIST // لیست خرید بعدی
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    toOrderScreen: () -> Unit,
    toDetailsScreen: (Product) -> Unit,
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(TabItem.SHOPPING_CART) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(
            modifier = Modifier.clickable {},
            selectedTabIndex = selectedTab.ordinal,
            containerColor = white,
        ) {
            TabItem.entries.forEach { tabItem ->
                Tab(
                    selected = selectedTab == tabItem,
                    onClick = { selectedTab = tabItem },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                when (tabItem) {
                                    TabItem.SHOPPING_CART -> "سبد خرید"
                                    TabItem.WISHLIST -> "لیست خرید بعدی"
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedTab == tabItem) primaryColor else gray
                            )
                            if (tabItem == TabItem.SHOPPING_CART && state.cartSummery.itemCount > 0) {
                                TabBadge(
                                    count = state.cartSummery.itemCount,
                                    selectedColor = selectedTab == tabItem
                                )
                            }
                        }
                    },
                    icon = {},
                    selectedContentColor = Color.Transparent,
                    unselectedContentColor = gray,
                )
            }
        }

        when (selectedTab) {
            TabItem.SHOPPING_CART -> {
                if (state.screenLoading) {
                    LoadingState()
                }

                ShoppingCartList(
                    onIncrement = viewModel::onIncrement,
                    onDecrement = viewModel::onDecrement,
                    onDelete = viewModel::deleteCartItem,
                    cartItemList = state.cartItems,
                    cartSummery = state.cartSummery,
                    navToOrder = toOrderScreen,
                    navToDetails = toDetailsScreen
                )
            }

            TabItem.WISHLIST -> EmptyState()
        }
    }
}

@Composable
private fun TabBadge(count: Int, selectedColor: Boolean) {


    Box(
        modifier = Modifier
            .width(28.dp)
            .height(32.dp)
            .padding(start = 8.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(22))
            .background(if (selectedColor) primaryColor else gray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.vazirmatn_bold)),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(top = 2.dp)
                .align(Alignment.Center)
        )
    }
}

