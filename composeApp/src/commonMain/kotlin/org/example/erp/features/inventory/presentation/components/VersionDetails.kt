package org.example.erp.features.inventory.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.at
import org.example.erp.features.user.domain.entity.User
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VersionDetails(
    sectionHeader: String,
    modifierName: String?,
    modificationTimestamp: String?,
    fetchUser: suspend (String) -> Result<User>,
    modifier: Modifier = Modifier,
) {
    var displayName by remember {
        mutableStateOf("")
    }

    LaunchedEffect(modifierName) {
        if (modifierName != null) {
            displayName = fetchUser(modifierName).getOrNull()?.name ?: ""
        }
    }

    AnimatedVisibility(modifierName != null && modificationTimestamp != null, modifier) {
        FlowRow {
            Text(
                "$sectionHeader: $displayName",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                " ${stringResource(Res.string.at)} $modificationTimestamp", maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}