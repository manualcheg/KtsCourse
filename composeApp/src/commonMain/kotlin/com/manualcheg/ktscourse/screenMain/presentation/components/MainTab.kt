package com.manualcheg.ktscourse.screenMain.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.ui.graphics.vector.ImageVector
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.main_tab_favorites
import ktscourse.composeapp.generated.resources.main_tab_launches
import ktscourse.composeapp.generated.resources.main_tab_rockets
import org.jetbrains.compose.resources.StringResource

enum class MainTab(val titleRes: StringResource, val icon: ImageVector) {
    Launches(Res.string.main_tab_launches, Icons.AutoMirrored.Filled.List),
    Rockets(Res.string.main_tab_rockets, Icons.Default.Rocket),
    Favorites(Res.string.main_tab_favorites, Icons.Default.Favorite)
}
