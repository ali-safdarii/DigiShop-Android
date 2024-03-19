package com.example.pickyshop.presentation.screens.auth.login_register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.digishop.presentation.screens.auth.login_register.LoginRegisterViewModel
import com.example.digishop.presentation.screens.auth.login_register.LoginRegsiterContent
import com.example.digishop.presentation.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRegisterScreen(
    navigatToHomeScreen: () -> Unit,
    viewModel: LoginRegisterViewModel = hiltViewModel()

) {

    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.viewState.collectAsStateWithLifecycle()



    NavDestinationHelper(
        shouldNavigate = {
            state.isSuccessfullyLoggedIn
        },
        destination = {
            navigatToHomeScreen()
        }
    )

    state.userMessage?.let { userMessage ->
        val snackbarText = stringResource(userMessage)
        LaunchedEffect(userMessage, snackbarText, viewModel) {
            snackbarHostState.showSnackbar(snackbarText)
            viewModel.userMessageShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Surface(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(), color = white
        ) {
            LoginRegsiterContent(
                modifier = Modifier
                    .background(color = white)
                    .fillMaxSize()
                    .padding(PaddingValues(all=16.dp))
                    .verticalScroll(
                        rememberScrollState()
                    ),
                onLoginButtonClick = viewModel::loginrRegisterButton,
                onTrailingPasswordIconClick = viewModel::onToggleVisualTransformation,
                onEmailChanged = viewModel::onEmailInputChange,
                onPasswordChanged = viewModel::onPasswordInputChange,
                emailValue = { state.credentials.email.value },
                passwordValue = { state.credentials.password.value },
                isPasswordShown = { state.isPasswordShowShown },
                isLoading = { state.isLoading },
                buttonEnabled = { state.isInputValid }

            )

        }

    }


}

@Composable
private fun NavDestinationHelper(
    shouldNavigate: () -> Boolean,
    destination: () -> Unit
) {
    LaunchedEffect(key1 = shouldNavigate()) {
        if (shouldNavigate()) {
            destination()
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun PreviewContent() {
    LoginRegsiterContent(
        emailValue = { "alisafdari@gmail.com" },
        passwordValue = { "12345678" },
        isLoading = { false },
        buttonEnabled = { true },
        onTrailingPasswordIconClick = { },
        onEmailChanged = {},
        onPasswordChanged = {},
        onLoginButtonClick = { },
        isPasswordShown = { false }
    )

}
*/
