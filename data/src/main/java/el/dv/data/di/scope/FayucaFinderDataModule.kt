package el.dv.data.di.scope

import el.dv.data.network.truck.repository.TruckRepositoryImpl
import el.dv.data.network.user.repository.UserRepositoryImpl
import el.dv.domain.truck.repository.TruckRepository
import el.dv.domain.user.UserRepository
import org.koin.dsl.module

/**
 * This module will hold all repository services
 */
val fayucaFinderDataModule = module {
    single<TruckRepository> {
        TruckRepositoryImpl(truckApi = get(), dispatchers = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(userApi = get(), dispatchers = get())
    }
}
