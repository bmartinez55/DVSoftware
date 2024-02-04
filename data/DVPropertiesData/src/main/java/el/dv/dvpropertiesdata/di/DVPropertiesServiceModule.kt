package el.dv.dvpropertiesdata.di

import android.content.Context
import androidx.room.Room
import el.dv.data.storage.core.api.DataStoreApi
import el.dv.data.storage.sharedpreferences.api.SharedPreferencesDataStoreApi
import el.dv.dvpropertiesdata.network.PROPERTY_DETAILS_DB
import el.dv.dvpropertiesdata.network.PropertyDetailsDB
import el.dv.dvpropertiesdata.util.DVPropertiesConst
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dVPropertiesServiceModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), PropertyDetailsDB::class.java, PROPERTY_DETAILS_DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<PropertyDetailsDB>().getPropertyDetailsDao()
    }

    single {
        androidContext().getSharedPreferences(DVPropertiesConst.SHARED_PREFS_FILE, Context.MODE_PRIVATE)
    }

    singleOf(::SharedPreferencesDataStoreApi) bind DataStoreApi::class
}
