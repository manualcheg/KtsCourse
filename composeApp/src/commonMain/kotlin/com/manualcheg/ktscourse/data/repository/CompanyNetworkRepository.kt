package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.CompanyDto

interface CompanyNetworkRepository {
    suspend fun getCompanyInfo(): Result<CompanyDto>
}
