package com.manualcheg.ktscourse.data.local_storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

object DataStorePreferencesProvider {
    lateinit var datastore: DataStore<Preferences>
}