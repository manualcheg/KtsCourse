package com.manualcheg.ktscourse.common

import androidx.compose.ui.platform.UriHandler
import io.github.aakira.napier.Napier

fun UriHandler.openSafeUri(url: String?) {
    if (url.isNullOrBlank()) return
    val finalUrl = if (url.startsWith("https") || url.startsWith("http")) {
        url
    } else {
        "https://$url"
    }
    try {
        this.openUri(finalUrl)
    } catch (e: Exception) {
        Napier.e("Failed to open uri: $url", e)
    }
}
