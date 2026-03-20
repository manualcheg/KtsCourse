package com.manualcheg.ktscourse

import androidx.compose.ui.window.ComposeUIViewController
import com.manualcheg.ktscourse.common.App
import com.manualcheg.ktscourse.data.createDataStore
import com.manualcheg.ktscourse.data.datastore.DataStorePreferencesProvider

fun MainViewController() = ComposeUIViewController {

    DataStorePreferencesProvider.datastore = createDataStore()
    App()
}