package com.example.digishop.presentation


import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.digishop.domain.session.SessionState
import com.example.digishop.presentation.component.AppBottomAppBar
import com.example.digishop.presentation.navigation.NavGraph
import com.example.digishop.presentation.navigation.Screens
import com.example.digishop.presentation.theme.MainAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sessionViewModel: SessionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        /*  enableEdgeToEdge(
              statusBarStyle = SystemBarStyle.light(
                  Color.WHITE, Color.WHITE
              ),
              navigationBarStyle = SystemBarStyle.light(
                  Color.WHITE, Color.WHITE
              )
          )*/
        super.onCreate(savedInstanceState)
        setContent {

            MainAppTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    MainScreen()
                }


                val sessionState = sessionViewModel.sessionState.collectAsState()

                when (sessionState.value) {
                    SessionState.UNINITIALIZED -> Timber.d("User: UNINITIALIZED")
                    SessionState.AUTHENTICATED -> Timber.d("User: LOGGED_IN ")
                    SessionState.UN_AUTHENTICATED -> Timber.d("User: UN_AUTHENTICATED ")
                }


            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    // God Example For hidden Views:https://stackoverflow.com/questions/66837991/hide-top-and-bottom-navigator-on-a-specific-screen-inside-scaffold-jetpack-compo

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    val topLevelDestinations = listOf(
        Screens.HomeScreen.route,
        Screens.CartScreen.route,
        Screens.ProfileScreen.route,
    )


    val showBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route in topLevelDestinations




        Scaffold(
            bottomBar = {
                if (showBottomBar)
                    AppBottomAppBar(navController = navController)
            }
        ) { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                /*  .statusBarsPadding()
                  .navigationBarsPadding()*/

            ) {

                NavGraph(navController)

            }
    }


}