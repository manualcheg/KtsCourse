package com.manualcheg.ktscourse.screenSettings.domain

data class History(
    val details: String,
    val eventDateUnix: Int,
    val eventDateUtc: String,
    val title: String,
    val article: String
)
