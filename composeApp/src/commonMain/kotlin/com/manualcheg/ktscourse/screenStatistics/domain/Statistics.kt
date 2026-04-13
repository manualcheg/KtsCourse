package com.manualcheg.ktscourse.screenStatistics.domain

data class Statistics(
    val totalLaunches: Int,
    val successLaunches: Int,
    val failedLaunches: Int,
    val upcomingLaunches: Int,
    val launchesByYear: Map<Int, Int> = emptyMap()
)
