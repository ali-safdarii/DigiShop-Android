package com.example.digishop.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digishop.domain.manager.usecase.AppEntryUseCases
import com.example.pickyshop.presentation.screens.cart.CartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class OnBoardingState(val appEntrySuccess: Boolean = false)

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {
    private val _viewState = MutableStateFlow(OnBoardingState())
    val viewState = _viewState.asStateFlow()


    init {
        readAppEntry()
    }

    fun saveAppEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry(true)
        }
    }


    private fun readAppEntry() {
        viewModelScope.launch {
            appEntryUseCases.readAppEntry.invoke().collect { appEntry ->
                _viewState.update {
                    Timber.d("appEntry: $appEntry")
                    it.copy(appEntrySuccess = appEntry)
                }
            }
        }
    }
}