package com.manualcheg.ktscourse.screenMain.domain.model

data class LaunchesPageResult(
    val launches: List<Launch>,
    val isLastPage: Boolean
)
