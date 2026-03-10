package com.manualcheg.ktscourse.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.models.Launch
import com.manualcheg.ktscourse.data.repository.NetworkRepositoryImpl
import com.manualcheg.ktscourse.presentation.ui.screens.UIStates.MainUiState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ViewModelMainScreen : ViewModel() {
    private val repository = NetworkRepositoryImpl()
    private var allLaunches: List<Launch> = emptyList()

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        searchWithDebounce()
        updateData()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun searchWithDebounce() {
        _searchQuery
            .debounce(1000L)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                flow {
                    val filtered = if (query.isBlank()) {
                        allLaunches
                    } else {
                        allLaunches.filter {
                            it.name.contains(query, ignoreCase = true) ||
                                    it.details.contains(query, ignoreCase = true)
                        }
                    }
                    emit(filtered)
                }
            }
            .onEach { filtered ->
                if (_uiState.value is MainUiState.Loading) return@onEach

                _uiState.value = when {
                    filtered.isEmpty() && allLaunches.isNotEmpty() -> MainUiState.Empty
                    allLaunches.isEmpty() && _uiState.value !is MainUiState.Error -> MainUiState.Empty
                    else -> MainUiState.Success(filtered)
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateData() {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            repository.getAllLaunches()
                .onSuccess {
                    allLaunches = it
                    _uiState.value = MainUiState.Success(it)
                    onSearchQueryChange(_searchQuery.value)
                }.onFailure {
                    Napier.e("Search error", it, tag = "Network")
                    _uiState.value = MainUiState.Error(it.message ?: "Unknown error")
                }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
