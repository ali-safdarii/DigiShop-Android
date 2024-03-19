package com.example.digishop.presentation.navigation

import CartScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.digishop.presentation.SessionViewModel
import com.example.digishop.presentation.navigation.ScreenArgs.CATEGORY_ID_ARG
import com.example.digishop.presentation.navigation.ScreenArgs.PRODUCT_ID_ARG
import com.example.digishop.presentation.navigation.ScreenDestinationsWithArgs.CATEGORY_ROUTE
import com.example.digishop.presentation.navigation.ScreenDestinationsWithArgs.DETAILS_ROUTE
import com.example.digishop.presentation.navigation.ScreenDestinationsWithArgs.GALLERIES_ROUTE
import com.example.digishop.presentation.navigation.ScreenDestinationsWithArgs.META_ROUTE
import com.example.digishop.presentation.navigation.ScreenDestinationsWithArgs.REVIEW_ROUTE
import com.example.digishop.domain.session.SessionState
import com.example.pickyshop.presentation.screens.auth.login_register.LoginRegisterScreen
import com.example.digishop.presentation.screens.category.CategoryByIdScreen
import com.example.digishop.presentation.screens.details.DetailsScreen
import com.example.pickyshop.presentation.screens.details.gallery.GalleryImageScreen
import com.example.digishop.presentation.screens.details.meta.MetaScreen
import com.example.digishop.presentation.screens.details.review.ReviewScreen
import com.example.digishop.presentation.screens.home.MainScreen
import com.example.digishop.presentation.screens.home.see_more.ProductScreen
import com.example.digishop.presentation.screens.onboarding.OnBoardingScreen
import com.example.digishop.presentation.screens.order.OrderScreen
import com.example.digishop.presentation.screens.profile.ProfileScreen
import com.example.digishop.presentation.screens.search.SearchScreen


object ScreenArgs {
    const val PRODUCT_ID_ARG = "productId"
    const val CATEGORY_ID_ARG = "catId"
}

object ScreenDestinationsWithArgs {
    val DETAILS_ROUTE = Screens.DetailScreen.route + "/{$PRODUCT_ID_ARG}"
    val META_ROUTE = Screens.MetaScreen.route + "/{$PRODUCT_ID_ARG}"
    val REVIEW_ROUTE = Screens.ReviewScreen.route + "/{$PRODUCT_ID_ARG}"
    val GALLERIES_ROUTE = Screens.GalleryScreen.route + "/{$PRODUCT_ID_ARG}"
    val CATEGORY_ROUTE = Screens.CategoryScreen.route + "/{$CATEGORY_ID_ARG}"

}

class NavigationActions(private val navController: NavHostController) {
    fun navigateToDetailsScreen(productId: Int) {
        navController.navigate(Screens.DetailScreen.route + "/$productId")
    }

    fun navigateToCategoryScreen(catId: Int) {
        navController.navigate(Screens.CategoryScreen.route + "/$catId")
    }

    fun toLoginRegsiterScreen() {
        navController.navigate(Screens.LoginRegisterScreen.route)
    }

    fun navigateToHomeScreen() {
        navController.navigate(Screens.HomeScreen.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }


    fun navigateToProductScreen() {
        navController.navigate(Screens.ProductsScreen.route)
    }


    fun toMetaScreen(productId: Int) {
        navController.navigate(Screens.MetaScreen.route + "/$productId")
    }

    fun toReviewScreen(productId: Int) {
        navController.navigate(Screens.ReviewScreen.route + "/$productId")
    }


    fun navigateToGalleryScreen(productId: Int) {
        navController.navigate(Screens.GalleryScreen.route + "/$productId")
    }


    fun toOrderScreen() {
        navController.navigate(Screens.OrderScreen.route)
    }

    fun toSearchScreen() {
        navController.navigate(Screens.SearchScreen.route)
    }

    fun toCartScreen() {
        navController.navigate(Screens.CartScreen.route)
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screens.OnBoardingScreen.route,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
    viewModel: SessionViewModel = hiltViewModel()
) {

    val sessionState by viewModel.sessionState.collectAsState()



    NavHost(
        navController = navController, startDestination = startDestination
    ) {


        composable(route = Screens.OnBoardingScreen.route) {
            OnBoardingScreen(navToHome = {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            })
        }

        composable(route = Screens.HomeScreen.route) {
            MainScreen(
                navigatToDetailsScreen = { product ->
                    navActions.navigateToDetailsScreen(product.id)
                },
                navigateToProductScreen = {
                    navActions.navigateToProductScreen()
                },
                navigateToSearchScreen = {
                    navActions.toSearchScreen()
                },
                navigatToCategoryScreen = { category ->
                    navActions.navigateToCategoryScreen(category.id)
                },
            )
        }



        composable(
            route = DETAILS_ROUTE,
            arguments = listOf(navArgument(PRODUCT_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(PRODUCT_ID_ARG)?.let { id ->

                DetailsScreen(
                    sessionState = sessionState,

                    onNavigateUp = {
                        navController.popBackStack()
                    },
                    toMetaScreen = { product ->
                        navActions.toMetaScreen(product.id)
                    },
                    toReviewScreen = { product ->
                        navActions.toReviewScreen(product.id)
                    },
                    navigateToAuthScreen = {
                        navActions.toLoginRegsiterScreen()
                    },
                    toGalleryScreen = { product ->
                        navActions.navigateToGalleryScreen(product.id)
                    },
                    toCartScreen = { navActions.toCartScreen() },
                )
            }
        }

        composable(route = Screens.CartScreen.route) {

            when (sessionState) {
                SessionState.UNINITIALIZED -> {

                }

                SessionState.AUTHENTICATED -> {
                    CartScreen(
                        
                        toOrderScreen = {
                            navActions.toOrderScreen()
                        },
                        toDetailsScreen = {
                            navActions.navigateToDetailsScreen(it.id)
                        }
                    )
                }

                SessionState.UN_AUTHENTICATED -> {
                    LoginRegisterScreen(navigatToHomeScreen = {})
                }
            }

        }

        composable(route = Screens.OrderScreen.route) {

            when (sessionState) {
                SessionState.UNINITIALIZED -> {

                }

                SessionState.AUTHENTICATED -> {
                    OrderScreen(
                        onNavigateUp = {
                            navController.popBackStack()
                        },
                    )
                }

                SessionState.UN_AUTHENTICATED -> {
                    LoginRegisterScreen(navigatToHomeScreen = {})
                }
            }
        }



        composable(route = Screens.ProfileScreen.route) {

            when (sessionState) {
                SessionState.UNINITIALIZED -> {

                }

                SessionState.AUTHENTICATED -> {
                    ProfileScreen()
                }

                SessionState.UN_AUTHENTICATED -> {
                    LoginRegisterScreen(navigatToHomeScreen = {})
                }
            }

        }


        composable(route = Screens.LoginRegisterScreen.route) {
            LoginRegisterScreen(navigatToHomeScreen = { navActions.navigateToHomeScreen() })
        }


        composable(
            route = REVIEW_ROUTE,
            arguments = listOf(navArgument(PRODUCT_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(PRODUCT_ID_ARG)?.let {
                ReviewScreen()
            }
        }

        composable(
            route = META_ROUTE,
            arguments = listOf(navArgument(PRODUCT_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->

            backStackEntry.arguments?.getInt(PRODUCT_ID_ARG)?.let {
                MetaScreen(onBackPressed = {
                    navController.popBackStack()
                })
            }

        }


        composable(
            route = GALLERIES_ROUTE,
            arguments = listOf(navArgument(PRODUCT_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->

            backStackEntry.arguments?.getInt(PRODUCT_ID_ARG)?.let {
                GalleryImageScreen(onBackPressed = { navController.popBackStack() })
            }

        }



        composable(
            route = CATEGORY_ROUTE,
            arguments = listOf(navArgument(CATEGORY_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(CATEGORY_ID_ARG)?.let {
                CategoryByIdScreen(navToDetails = { product ->
                    navActions.navigateToDetailsScreen(product.id)
                })

            }
        }



        composable(route = Screens.ProductsScreen.route) {
            ProductScreen(navToDetails = { product ->
                navActions.navigateToDetailsScreen(product.id)
            })
        }

        composable(route = Screens.SearchScreen.route) {
            SearchScreen(navToDetails = { product ->
                navActions.navigateToDetailsScreen(product.id)
            })
        }

    }

}

