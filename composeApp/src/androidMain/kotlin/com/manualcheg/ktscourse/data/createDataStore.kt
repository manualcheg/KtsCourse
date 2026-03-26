package com.manualcheg.ktscourse.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.manualcheg.ktscourse.data.datastore.DATA_STORE_FILE_NAME
import com.manualcheg.ktscourse.data.datastore.createDataStore

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
)
