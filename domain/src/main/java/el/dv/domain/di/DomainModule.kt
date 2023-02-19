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
import el.dv.domain.truck.usecase.AddTruckUseCase
import el.dv.domain.truck.usecase.GetTruckDetailsUseCase
import el.dv.domain.truck.usecase.GetTrucksFromOwnerUseCase
import el.dv.domain.truck.usecase.GetTrucksUseCase
import el.dv.domain.user.usecase.AddUserUseCase
import el.dv.domain.user.usecase.GetUserUseCase
import el.dv.domain.user.usecase.UpdateUserUseCase
import org.koin.dsl.module

val domainModule = module {
    /**
     * Domain class implementations
     */
    single<CoroutineDispatchers> {
        AppCoroutineDispatchers()
    }

    single<EventBus> {
        EventBusImpl()
    }

    /**
     * Use Cases
     */
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

    factory {
        AddTruckUseCase(truckRepository = get())
    }

    factory {
        GetTruckDetailsUseCase(truckRepository = get())
    }

    factory {
        GetTrucksUseCase(truckRepository = get())
    }

    factory {
        GetTrucksFromOwnerUseCase(truckRepository = get())
    }

    factory {
        AddUserUseCase(userRepository = get())
    }

    factory {
        UpdateUserUseCase(userRepository = get())
    }

    factory {
        GetUserUseCase(userRepository = get())
    }
}
