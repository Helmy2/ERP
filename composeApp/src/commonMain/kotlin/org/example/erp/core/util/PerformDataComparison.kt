package org.example.erp.core.util

import org.example.erp.core.domain.entity.DataChange

fun <T, K> performDataComparison(
    localData: List<T>,
    remoteData: List<T>,
    keySelector: (T) -> K,
    areItemsDifferent: (T, T) -> Boolean
): DataChange<T> {
    // Create maps for efficient lookups
    val localMap = localData.associateBy(keySelector)
    val remoteMap = remoteData.associateBy(keySelector)

    // Elements to delete: present locally but not remotely
    val toDelete = localData.filter { !remoteMap.containsKey(keySelector(it)) }

    // Elements to insert/update:
    // 1. New items (not in local)
    // 2. Existing items with changes
    val toInsert = remoteData.filter { remoteItem ->
        val localItem = localMap[keySelector(remoteItem)]
        localItem == null || areItemsDifferent(remoteItem, localItem)
    }

    return DataChange(
        toDelete = toDelete,
        toInsert = toInsert
    )
}