package com.manualcheg.ktscourse.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.repository.NetworkRepositoryImpl
import com.manualcheg.ktscourse.presentation.ui.screens.UIStates.MainUiState
import kotlinx.coroutines.launch

class ViewModelMainScreen : ViewModel() {
    private val repository = NetworkRepositoryImpl()

    var uiState by mutableStateOf<MainUiState>(MainUiState.Loading)
        private set

    init {
        updateData()
    }

    fun updateData() {
        viewModelScope.launch {
            uiState = MainUiState.Loading
            repository.getAllLaunches()
                .onSuccess {
                    uiState = MainUiState.Success(it)
                }.onFailure {
                    uiState = MainUiState.Error(it.message ?: "Unknown error")
                }
        }
    }
}
