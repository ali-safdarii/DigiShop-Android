package com.example.digishop.presentation.navigation

sealed class Screens(val route: String) {
    data object OnBoardingScreen : Screens("intro_screen")
    data object DetailScreen : Screens("detail_screen")
    data object GalleryScreen : Screens("preview_screen")
    data object ProductsScreen : Screens("products_screen")
    data object ReviewScreen : Screens("review_screen")
    data object MetaScreen : Screens("meta_screen")
    data object CategoryScreen : Screens("category")
    data object OrderScreen : Screens("order_Screen")
    data object LoginRegisterScreen : Screens("login_register_Screen")
    data object SearchScreen : Screens("search_screen")

    // BottomBar
    data object HomeScreen : Screens("home_screen")
    data object ProfileScreen : Screens("profile_screen")
    data object CartScreen : Screens("cart_screen")

}
