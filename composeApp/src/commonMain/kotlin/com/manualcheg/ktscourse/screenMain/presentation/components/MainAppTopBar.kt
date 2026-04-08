package com.manualcheg.ktscourse.screenMain.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.manualcheg.ktscourse.common.LocalDimensions
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.ic_close_24dp
import ktscourse.composeapp.generated.resources.ic_search_24dp
import ktscourse.composeapp.generated.resources.main_screen_docked_search_bar_placeholder
import ktscourse.composeapp.generated.resources.main_screen_icon_clear_field_content_description
import ktscourse.composeapp.generated.resources.main_screen_icon_search_content_description
import ktscourse.composeapp.generated.resources.main_screen_profile_icon_content_description
import ktscourse.composeapp.generated.resources.main_tab_favorites
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onProfileClick: () -> Unit,
    showSearch: Boolean = true
) {
    val focusManager = LocalFocusManager.current
    val dimensions = LocalDimensions.current

    TopAppBar(
        title = {
            if (showSearch) {
                DockedSearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = searchQuery,
                            onQueryChange = { onSearchQueryChange(it) },
                            onSearch = { focusManager.clearFocus() },
                            expanded = false,
                            onExpandedChange = { },
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(end = dimensions.paddingMedium),
                            placeholder = { Text(stringResource(Res.string.main_screen_docked_search_bar_placeholder)) },
                            leadingIcon = {
                                Icon(
                                    painterResource(Res.drawable.ic_search_24dp),
                                    contentDescription = stringResource(Res.string.main_screen_icon_search_content_description),
                                    modifier = Modifier.size(dimensions.iconSize),
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(
                                        onClick = {
                                            onSearchQueryChange("")
                                        },
                                    ) {
                                        Icon(
                                            painterResource(Res.drawable.ic_close_24dp),
                                            contentDescription = stringResource(Res.string.main_screen_icon_clear_field_content_description),
                                            modifier = Modifier.size(dimensions.iconSize),
                                        )
                                    }
                                }
                            },
                        )
                    },
                    expanded = false,
                    onExpandedChange = { },
                    modifier = Modifier.fillMaxWidth().padding(end = dimensions.paddingMedium),
                    content = { },
                )
            } else {
                Text(stringResource(Res.string.main_tab_favorites))
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(Res.string.main_screen_profile_icon_content_description),
                    modifier = Modifier.size(dimensions.iconSize),
                )
            }
        },
    )
}

@Preview
@Composable
fun PreviewMainAppTopBar() {
    MainTopAppBar("", {}, {})
}
