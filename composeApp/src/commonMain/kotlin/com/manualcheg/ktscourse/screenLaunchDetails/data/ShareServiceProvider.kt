package com.manualcheg.ktscourse.screenLaunchDetails.data

expect class ShareServiceProvider() {
    fun getShareService(): ShareService
}

fun shareContent(provider: ShareServiceProvider, text: String = "") {
    provider.getShareService().share(text)
}
