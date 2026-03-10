package com.manualcheg.ktscourse.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.models.Launch
import com.manualcheg.ktscourse.data.repository.NetworkRepositoryImpl
import com.manualcheg.ktscourse.presentation.ui.screens.uistates.MainUiState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.network_error
import kotlin.coroutines.coroutineContext

class ViewModelMainScreen : ViewModel() {
    private val repository = NetworkRepositoryImpl()
    private var allLaunches: MutableList<Launch> = mutableListOf()
    private var currentPage = 1
    private var isLastPage = false
    private var nextPageJob: Job? = null

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isNextPageLoading = MutableStateFlow(false)
    val isNextPageLoading: StateFlow<Boolean> = _isNextPageLoading.asStateFlow()

    init {
        search()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun search() {
        _searchQuery
            .debounce(1000L)
            .distinctUntilChanged()
            .onEach {
                nextPageJob?.cancel()
                resetPagination()
            }
            .flatMapLatest { query -> loadPage(query, 1) }
            .launchIn(viewModelScope)
    }

    private fun loadPage(query: String, page: Int) = flow<Unit> {
        if (page == 1) {
            _uiState.value = MainUiState.Loading
        } else {
            _isNextPageLoading.value = true
        }

        repository.getAllLaunches(query, page)
            .onSuccess { response ->
                if (!currentCoroutineContext().isActive) return@onSuccess
                isLastPage = !response.hasNextPage
                if (page == 1) {
                    allLaunches = response.docs.toMutableList()
                } else {
                    allLaunches.addAll(response.docs)
                }

                if (allLaunches.isEmpty()) {
                    _uiState.value = MainUiState.Empty
                } else {
                    _uiState.value = MainUiState.Success(allLaunches.toList())
                }
                _isNextPageLoading.value = false
            }
            .onFailure {
                if (!currentCoroutineContext().isActive) return@onFailure
                if (page == 1) {
                    Napier.e(it.message.toString(), it, Res.string.network_error.toString())
                    _uiState.value =
                        MainUiState.Error(it.message ?: Res.string.network_error.toString())
                }
                _isNextPageLoading.value = false
            }
    }

    fun loadNextPage() {
        if (isLastPage || _isNextPageLoading.value || _uiState.value is MainUiState.Loading) return
//        currentPage++
        nextPageJob = viewModelScope.launch {
            loadPage(_searchQuery.value, currentPage).collect()
        }
    }

    private fun resetPagination() {
        currentPage = 1
        isLastPage = false
        allLaunches.clear()
        _isNextPageLoading.value = false
    }

    fun updateData() {
        nextPageJob?.cancel()
        resetPagination()
        viewModelScope.launch {
            loadPage(_searchQuery.value, 1).collect()
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
