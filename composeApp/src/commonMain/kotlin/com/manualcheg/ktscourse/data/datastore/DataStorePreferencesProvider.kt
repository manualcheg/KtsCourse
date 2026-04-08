package com.manualcheg.ktscourse.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun provideDataStore(context: Any? = null): DataStore<Preferences>
