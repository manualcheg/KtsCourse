package com.manualcheg.ktscourse.presentation.ui.screens

import com.manualcheg.ktscourse.data.Launch

data class MainUiState(
    val name: String = "",
    val listOfLaunches: List<Launch> = listOf()
)
