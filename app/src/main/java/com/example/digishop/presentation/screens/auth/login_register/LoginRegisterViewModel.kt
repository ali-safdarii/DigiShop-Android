package com.example.digishop.presentation.screens.auth.login_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digishop.R
import com.example.digishop.data.remote.util.onFailure
import com.example.digishop.data.remote.util.onSuccess
import com.example.digishop.domain.auth.model.Email
import com.example.digishop.domain.auth.model.LoginRegisterRequest
import com.example.digishop.domain.auth.model.Password
import com.example.digishop.domain.auth.model.ValidationType
import com.example.digishop.domain.auth.usecase.LoginOrRegisterUseCase
import com.example.digishop.domain.auth.usecase.ValidationInputsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class LoginRegisterState(
    val isLoading: Boolean = false,
    val isInputValid: Boolean = false,
    val isPasswordShowShown: Boolean = false,
    val isSuccessfullyLoggedIn: Boolean = false,
    val userMessage: Int? = null,
    val credentials: LoginRegisterRequest = LoginRegisterRequest()

)

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val loginOrRegister: LoginOrRegisterUseCase,
    private val validationInputsUseCase: ValidationInputsUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow(LoginRegisterState())
    val viewState = _viewState.asStateFlow()


    fun loginrRegisterButton() {
        checkInputValidation()
        val credentials = _viewState.value.credentials
        handleResult(credentials)
    }


    fun onEmailInputChange(newValue: String) {
        val currentPassword =  _viewState.value.credentials.password.value
        _viewState.update {
            it.copy(
                credentials = LoginRegisterRequest(email = Email(newValue), password = Password(currentPassword))
            )
        }

    }


    fun onPasswordInputChange(newValue: String) {
        val oldEmail = _viewState.value.credentials.email.value

        _viewState.update {
            it.copy(
                credentials = LoginRegisterRequest(email = Email(oldEmail), password = Password(newValue))
            )
        }
    }


    fun onToggleVisualTransformation() {
        _viewState.update {
            it.copy(
                isPasswordShowShown = !_viewState.value.isPasswordShowShown
            )
        }
    }


    private fun checkInputValidation() {
        val validationResult = validationInputsUseCase(request = _viewState.value.credentials)
        proccessInputValidationType(validationResult)
    }

    private fun proccessInputValidationType(type: ValidationType) {
        when (type) {
            ValidationType.EmptyField -> {

                showMessage(R.string.empty_fields_message)
            }

            ValidationType.InvalidEmail -> {

                showMessage(R.string.email_validation_failed_message)
            }

            ValidationType.PasswordTooShort -> {
                showMessage(R.string.password_lengh_failed_message)
            }

            ValidationType.Valid -> {
                _viewState.update {
                    it.copy(
                        isInputValid = true
                    )
                }
            }
        }
    }

    private fun handleResult(credentials: LoginRegisterRequest) {
        _viewState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            loginOrRegister.invoke(credentials)
                .onSuccess { user ->
                    showMessage(R.string.success_login_message)
                    _viewState.update {
                        it.copy(
                            isSuccessfullyLoggedIn = true,
                            isLoading = false
                        )
                    }
                }.onFailure { failure ->
                    failure.errorTypeMessage.stringRes?.let { showMessage(it) }
                    failure.errorTypeMessage.localMessage?.let { Timber.e(it) }

                    _viewState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
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