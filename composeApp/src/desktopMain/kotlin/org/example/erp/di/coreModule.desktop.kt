package org.example.erp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.example.erp.core.data.core.AppDatabase
import org.example.erp.core.util.Connectivity
import org.example.erp.core.util.ConnectivityImp
import org.example.erp.core.util.PREFERENCES_NAME
import org.example.erp.core.util.createDataStore
import org.example.erp.core.util.getPlatformPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp()
    }
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = { getPlatformPath() + PREFERENCES_NAME }
        )
    }
    single<AppDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "erp.db")
        Room.databaseBuilder<AppDatabase>(
            dbFile.absolutePath,
        ).fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO).build()
    }
}

