package org.example.erp.features.inventory.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.at
import org.jetbrains.compose.resources.stringResource

@Composable
fun VersionDetails(
    sectionHeader: String,
    modifierName: String?,
    modificationTimestamp: String?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(modifierName != null && modificationTimestamp != null, modifier) {
        Row {
            Text("$sectionHeader: $modifierName")
            Text(" ${stringResource(Res.string.at)} $modificationTimestamp")
        }
    }
}