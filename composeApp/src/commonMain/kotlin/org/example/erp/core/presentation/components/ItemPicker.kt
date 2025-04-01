package org.example.erp.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.add_item
import erp.composeapp.generated.resources.no_categories_found
import erp.composeapp.generated.resources.parent_category_error
import erp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T> ItemPicker(
    forbiddenItemCodes: List<String>,
    itemCode: String? = null,
    onItemCodeChanged: (String?) -> Unit,
    isDialogVisible: Boolean,
    availableItems: List<T>,
    onDialogVisibilityChanged: (Boolean) -> Unit,
    onAddNewItem: ((String) -> Unit),
    onItemClicked: (T?) -> Unit,
    label: String,
    itemLabel: (T) -> String,
    matchesItemCode: (String?, T) -> Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = itemCode ?: "",
                onValueChange = onItemCodeChanged,
                minLines = 1,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            IconButton(
                onClick = { onDialogVisibilityChanged(true) },
            ) {
                Icon(
                    Icons.Default.Search, contentDescription = stringResource(Res.string.search)
                )
            }
        }

        val invalidCode = forbiddenItemCodes.contains(itemCode)

        val isNewItem = forbiddenItemCodes.contains(itemCode) || availableItems.any {
            matchesItemCode(itemCode, it)
        }.not()

        AnimatedVisibility(isNewItem && itemCode?.isNotBlank() == true) {
            Card(
                onClick = {
                    if (isNewItem) {
                        onAddNewItem(itemCode!!)
                    }
                },
                modifier = Modifier.padding(8.dp),
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (invalidCode.not()) Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(Res.string.add_item)
                    )
                    Text(
                        text = if (invalidCode) {
                            stringResource(Res.string.parent_category_error)
                        } else {
                            stringResource(Res.string.no_categories_found)
                        }
                    )
                }
            }
        }
    }

    AnimatedVisibility(isDialogVisible) {
        Dialog(
            onDismissRequest = { onDialogVisibilityChanged(false) },
        ) {
            ItemGrid(
                list = availableItems,
                onItemClick = {
                    onDialogVisibilityChanged(false)
                    onItemClicked(it)
                },
                labelProvider = itemLabel,
                isSelected = { matchesItemCode(itemCode, it) },
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }
}