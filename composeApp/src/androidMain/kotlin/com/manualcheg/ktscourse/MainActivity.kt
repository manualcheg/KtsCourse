package com.manualcheg.ktscourse

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.manualcheg.ktscourse.presentation.theme.AppThemeMaterial
//import ru.ivk1800.riflesso.Riflesso

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

//        Riflesso.initialize()
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
