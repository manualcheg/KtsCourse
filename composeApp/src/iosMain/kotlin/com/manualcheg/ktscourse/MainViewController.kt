package com.manualcheg.ktscourse

import androidx.compose.ui.window.ComposeUIViewController
import com.manualcheg.ktscourse.data.createDataStore
import com.manualcheg.ktscourse.data.local_storage.DataStorePreferencesProvider

fun MainViewController() = ComposeUIViewController {

    DataStorePreferencesProvider.datastore = createDataStore()
    App()
}