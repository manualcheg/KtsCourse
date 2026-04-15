package com.manualcheg.ktscourse.data.mappers

import com.manualcheg.ktscourse.data.models.HistoryDto
import com.manualcheg.ktscourse.screenSettings.domain.History

fun HistoryDto.toDomain(): History {
    return History(
        details = details,
        eventDateUnix = eventDateUnix,
        eventDateUtc = eventDateUtc,
        title = title,
        article = links.article.orEmpty(),
    )
}

fun List<HistoryDto>.toDomain(): List<History> {
    return map { it.toDomain() }
}
