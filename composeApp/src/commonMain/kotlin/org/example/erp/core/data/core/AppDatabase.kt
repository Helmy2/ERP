@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.example.erp.core.data.core

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import org.example.erp.core.data.core.converter.InstantConverter
import org.example.erp.features.inventory.data.local.dao.InventoryDao
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse


@Database(
    entities = [UnitsOfMeasureResponse::class], version = 2
)
@TypeConverters(InstantConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}