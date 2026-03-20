package com.manualcheg.ktscourse.screenMain.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.database.DatabaseHolder
import com.manualcheg.ktscourse.screenMain.domain.models.Launch
import com.manualcheg.ktscourse.data.repository.LaunchRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepositoryImpl
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
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
import kotlinx.coroutines.launch

class ViewModelMainScreen : ViewModel() {
    private val networkRepository = NetworkRepositoryImpl()
    private val launchRepository = LaunchRepository(
        networkRepository = networkRepository,
        launchDao = DatabaseHolder.database.launchDao()
    )

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

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isLastPageFlow = MutableStateFlow(false)
    val isLastPageFlow: StateFlow<Boolean> = _isLastPageFlow.asStateFlow()

    private val PAGE_SIZE = 10

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
        if (page == 1 && !_isRefreshing.value) {
            _uiState.value = MainUiState.Loading
        } else if (page > 1) {
            _isNextPageLoading.value = true
        }

        try {
            val networkResult = launchRepository.fetchAndSaveLaunches(query, page)
            if (networkResult.isSuccess) {
                isLastPage = !networkResult.getOrThrow()
            }

            val pagedData = launchRepository.getPagedLaunchesFromDb(query, page, PAGE_SIZE)

            if (page == 1) {
                allLaunches = pagedData.toMutableList()
            } else {
                allLaunches.addAll(pagedData)
            }

            if (pagedData.size < PAGE_SIZE) {
                isLastPage = true
            }

            if (allLaunches.isEmpty()) {
                if (networkResult.isFailure && page == 1) {
                    _uiState.value = MainUiState.Error("No internet and cache is empty")
                } else {
                    _uiState.value = MainUiState.Empty
                }
            } else {
                _uiState.value = MainUiState.Success(allLaunches.toList())
            }

        } catch (e: Exception) {
            Napier.e("Load error", e)
            if (page == 1) _uiState.value = MainUiState.Error(e.message ?: "Unknown error")
        } finally {
            _isLastPageFlow.value = isLastPage
            _isNextPageLoading.value = false
            _isRefreshing.value = false
        }
    }

    fun refresh() {
        nextPageJob?.cancel()
        resetPagination()
        _isRefreshing.value = true
        viewModelScope.launch {
            loadPage(_searchQuery.value, 1).collect()
        }
    }

    fun loadNextPage() {
        if (isLastPage || _isNextPageLoading.value || _uiState.value is MainUiState.Loading) return
        currentPage++
        nextPageJob = viewModelScope.launch {
            loadPage(_searchQuery.value, currentPage).collect()
        }
    }

    private fun resetPagination() {
        currentPage = 1
        isLastPage = false
        _isLastPageFlow.value = false
        allLaunches.clear()
        _isNextPageLoading.value = false
    }

    fun updateData() {
        refresh()
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}