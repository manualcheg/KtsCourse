package com.manualcheg.ktscourse.screenMain.domain.model

import com.manualcheg.ktscourse.domain.model.Launch

data class LaunchesPageResult(
    val launches: List<Launch>,
    val isLastPage: Boolean,
    val isFromCache: Boolean = false,
)
