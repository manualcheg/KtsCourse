package com.manualcheg.ktscourse.screenAbout.presentation

import com.manualcheg.ktscourse.data.models.CompanyDto
import org.jetbrains.compose.resources.StringResource

data class AboutUiState(
    val companyInfo: CompanyDto? = null,
    val isLoading: Boolean = false,
    val error: StringResource? = null
)
