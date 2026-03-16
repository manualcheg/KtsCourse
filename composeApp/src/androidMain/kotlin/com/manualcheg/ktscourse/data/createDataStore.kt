package com.manualcheg.ktscourse.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.manualcheg.ktscourse.data.local_storage.DATA_STORE_FILE_NAME
import com.manualcheg.ktscourse.data.local_storage.createDataStore

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
)
