package el.dv.data.di

import el.dv.data.storage.core.api.DataStoreApi
import el.dv.data.storage.sharedpreferences.api.SharedPreferencesDataStoreApi
import el.dv.data.storage.sharedpreferences.repository.SharedPreferencesDataStoreRepositoryImpl
import el.dv.domain.sharedpreferences.DataStoreRepository
import org.koin.dsl.module

val commonServiceModule = module {
    single<DataStoreApi> {
        SharedPreferencesDataStoreApi(sharedPref = get(), dispatchers = get())
    }

    single<DataStoreRepository> {
        SharedPreferencesDataStoreRepositoryImpl(dataStoreApi = get())
    }
}
