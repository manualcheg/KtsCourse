package com.manualcheg.ktscourse.presentation.ui

import androidx.lifecycle.ViewModel
import com.manualcheg.ktscourse.data.Launch
import com.manualcheg.ktscourse.data.SampleData
import com.manualcheg.ktscourse.presentation.ui.screens.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelMainScreen : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        updateData()
    }

    fun getList(): List<Launch> {
        return SampleData.launchesSample
    }

    fun updateData() {
        _uiState.update {
            it.copy(
                listOfLaunches = getList()
            )
        }
    }
}