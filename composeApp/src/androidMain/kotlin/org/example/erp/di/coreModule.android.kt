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
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp(context = androidApplication())
    }
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = {
                androidApplication().filesDir.resolve(PREFERENCES_NAME).absolutePath
            }
        )
    }
    single<AppDatabase> {
        val dbFile = androidApplication().getDatabasePath("erp.db")
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            dbFile.absolutePath
        ).fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO).build()
    }
}