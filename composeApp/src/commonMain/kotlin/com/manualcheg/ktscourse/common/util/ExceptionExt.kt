package com.manualcheg.ktscourse.common.util

import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.noInternet
import ktscourse.composeapp.generated.resources.unknown_error
import org.jetbrains.compose.resources.StringResource
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.utils.io.errors.IOException

fun Throwable.toUserFriendlyMessage(): StringResource {
    return when (this) {
        is kotlinx.io.IOException,
        is ConnectTimeoutException,
        is SocketTimeoutException,
        is HttpRequestTimeoutException -> Res.string.noInternet
        else -> Res.string.unknown_error
    }
}
