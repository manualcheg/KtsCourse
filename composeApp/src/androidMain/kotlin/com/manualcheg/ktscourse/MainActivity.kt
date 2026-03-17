package com.manualcheg.ktscourse

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.manualcheg.ktscourse.data.createDataStore
import com.manualcheg.ktscourse.data.database.DatabaseHolder
import com.manualcheg.ktscourse.data.database.appContext
import com.manualcheg.ktscourse.data.database.getAppDatabase
import com.manualcheg.ktscourse.data.database.getDatabaseBuilder
import com.manualcheg.ktscourse.data.local_storage.DataStorePreferencesProvider
import com.manualcheg.ktscourse.presentation.theme.AppThemeMaterial

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        appContext = applicationContext
        DatabaseHolder.database = getAppDatabase(getDatabaseBuilder())
        DataStorePreferencesProvider.datastore = createDataStore(context = this)

        setContent {
            AppThemeMaterial() {
                App()
            }
        }
    }
}

@Preview(name = "Light Mode")

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun AppAndroidPreview() {
    AppThemeMaterial() {
        App()
    }
}
