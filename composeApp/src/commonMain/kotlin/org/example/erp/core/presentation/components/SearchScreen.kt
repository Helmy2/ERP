package org.example.erp.core.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.create
import erp.composeapp.generated.resources.created_by
import erp.composeapp.generated.resources.delete
import erp.composeapp.generated.resources.search
import erp.composeapp.generated.resources.update
import erp.composeapp.generated.resources.updated_by
import org.example.erp.core.util.toLocalString
import org.example.erp.features.inventory.domain.entity.Item
import org.example.erp.features.inventory.presentation.components.VersionDetails
import org.example.erp.features.user.domain.entity.User
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    query: String,
    isSearchActive: Boolean,
    loading: Boolean,
    isNew: Boolean,
    selectedItem: Item?,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    fetchUser: suspend (String) -> Result<User>,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onCreate: () -> Unit,
    onUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    searchResults: @Composable () -> Unit,
    mainContent: @Composable () -> Unit,
) {
    BackHandler {
        if (isSearchActive) onSearchActiveChange(false)
        else onBack()
    }
    Box(
        modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp).fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        DockedSearchBar(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackButton(
                        onClick = {
                            if (isSearchActive) onSearchActiveChange(false)
                            else onBack()
                        },
                    )
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = onSearch,
                        expanded = isSearchActive,
                        onExpandedChange = onSearchActiveChange,
                        placeholder = { Text(stringResource(Res.string.search)) },
                        trailingIcon = {
                            IconButton(onClick = { onSearch(query) }) {
                                Icon(Icons.Default.Search, contentDescription = null)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            },
            expanded = isSearchActive,
            onExpandedChange = onSearchActiveChange,
        ) {
            AnimatedContent(loading) {
                if (it) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    searchResults()
                }
            }
        }
        Column(
            modifier = Modifier.padding(top = 64.dp).fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            mainContent()
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column {
                    VersionDetails(
                        sectionHeader = stringResource(Res.string.created_by),
                        modifierName = selectedItem?.createdBy,
                        fetchUser = fetchUser,
                        modificationTimestamp = selectedItem?.createdAt?.toLocalString(),
                    )
                    VersionDetails(
                        sectionHeader = stringResource(Res.string.updated_by),
                        modifierName = selectedItem?.updatedBy,
                        fetchUser = fetchUser,
                        modificationTimestamp = selectedItem?.updatedAt?.toLocalString(),
                    )
                }
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ElevatedButton(
                        onClick = {
                            onDelete()
                        },
                        enabled = !isNew && !loading,
                    ) {
                        Text(stringResource(Res.string.delete))
                    }
                    Button(
                        onClick = {
                            if (isNew) {
                                onCreate()
                            } else {
                                onUpdate()
                            }
                        },
                        enabled = !loading,
                    ) {
                        Text(
                            stringResource(
                                if (isNew) Res.string.create
                                else Res.string.update
                            )
                        )
                    }
                }
            }

        }
    }
}