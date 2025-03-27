package org.example.erp.features.inventory.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.at
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VersionDetails(
    sectionHeader: String,
    modifierName: String?,
    modificationTimestamp: String?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(modifierName != null && modificationTimestamp != null, modifier) {
        FlowRow {
            Text(
                "$sectionHeader: $modifierName",
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