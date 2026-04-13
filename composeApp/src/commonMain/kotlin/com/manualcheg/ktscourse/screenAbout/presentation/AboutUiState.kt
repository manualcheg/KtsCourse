package com.manualcheg.ktscourse.screenAbout.presentation

import com.manualcheg.ktscourse.data.models.CompanyDto

data class AboutUiState(
    val companyInfo: CompanyDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
