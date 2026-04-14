package com.manualcheg.ktscourse.screenAbout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.common.util.toUserFriendlyMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelAboutScreen(
    private val repository: NetworkRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()

    init {
        loadCompanyInfo()
    }

    fun loadCompanyInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getCompanyInfo()
                .onSuccess { info ->
                    _uiState.update { it.copy(companyInfo = info, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.toUserFriendlyMessage(), isLoading = false) }
                }
        }
    }
}
