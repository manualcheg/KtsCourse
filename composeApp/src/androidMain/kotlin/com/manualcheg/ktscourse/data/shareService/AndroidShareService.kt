package com.manualcheg.ktscourse.data.shareService

import android.content.Context
import android.content.Intent

class AndroidShareService(val context: Context) : ShareService {

    override fun share(text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(shareIntent, "Share via")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}
