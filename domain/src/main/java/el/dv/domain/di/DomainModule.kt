package el.dv.domain.di

import el.dv.domain.core.AppCoroutineDispatchers
import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.event.EventBus
import el.dv.domain.event.EventBusImpl
import el.dv.domain.networkmonitor.usecase.StartNetworkConnectivityMonitorUseCase
import el.dv.domain.sharedpreferences.usecase.LoadBooleanFromSharedPreferencesUseCase
import el.dv.domain.sharedpreferences.usecase.LoadStringFromSharedPreferencesUseCase
import el.dv.domain.sharedpreferences.usecase.SaveBooleanInSharedPreferencesUseCase
import el.dv.domain.sharedpreferences.usecase.SaveStringInSharedPreferencesUseCase
import org.koin.dsl.module

val domainModule = module {
    single<CoroutineDispatchers> {
        AppCoroutineDispatchers()
    }

    single<EventBus> {
        EventBusImpl()
    }

    factory {
        SaveStringInSharedPreferencesUseCase(dataStoreRepository = get())
    }

    factory {
        SaveBooleanInSharedPreferencesUseCase(dataStoreRepository = get())
    }

    factory {
        LoadStringFromSharedPreferencesUseCase(dataStoreRepository = get())
    }

    factory {
        LoadBooleanFromSharedPreferencesUseCase(dataStoreRepository = get())
    }

    single {
        StartNetworkConnectivityMonitorUseCase(networkConnectivityMonitor = get())
    }
}
