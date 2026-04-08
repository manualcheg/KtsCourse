package com.manualcheg.ktscourse.data.shareService

expect class ShareServiceProvider() {
    fun getShareService(): ShareService
}

fun shareContent(provider: ShareServiceProvider, text: String = "") {
    provider.getShareService().share(text)
}
