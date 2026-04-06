package com.manualcheg.ktscourse.screenLaunchDetails.data

import android.content.Context
import org.koin.mp.KoinPlatform.getKoin

actual class ShareServiceProvider actual constructor() {
    actual fun getShareService(): ShareService {
        val appContext = getKoin().get<Context>()
        return AndroidShareService(appContext)
    }
}
