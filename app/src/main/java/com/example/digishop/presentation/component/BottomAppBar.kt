package com.example.digishop.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.digishop.presentation.navigation.Screens
import com.example.digishop.presentation.theme.black
import com.example.digishop.presentation.theme.lightGray
import com.example.digishop.presentation.theme.textprimaryColor
import com.example.digishop.presentation.theme.white


 data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
)


private val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "خانه",
        route = Screens.HomeScreen.route,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),

    BottomNavigationItem(
        title = "سبد خرید",
        route = Screens.CartScreen.route,
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        badgeCount = null
    ),


    BottomNavigationItem(
        title = "پروفایل",
        route = Screens.ProfileScreen.route,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomAppBar(navController: NavController) {
    // Initialize selected item index
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    // Observe navigation changes to update selected item index
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    selectedItemIndex = bottomNavigationItems.indexOfFirst { it.route == currentRoute }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        border = BorderStroke(width = 1.dp, color = lightGray),
        shape = RoundedCornerShape(0.dp)
    ) {
        NavigationBar(
            containerColor = white,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            bottomNavigationItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.route)
                    },
                    icon = {
                        BadgedBox(badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            }
                        }) {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = textprimaryColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = black,
                        indicatorColor = white
                    )
                )
            }
        }
    }
}