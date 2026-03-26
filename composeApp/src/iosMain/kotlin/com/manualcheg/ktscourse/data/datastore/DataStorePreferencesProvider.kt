package com.manualcheg.ktscourse.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.manualcheg.ktscourse.data.createDataStore

actual fun provideDataStore(context: Any?): DataStore<Preferences> {
    return createDataStore()
}
