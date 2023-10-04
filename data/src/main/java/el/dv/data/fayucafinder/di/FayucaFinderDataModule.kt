package el.dv.data.fayucafinder.di

import el.dv.data.fayucafinder.network.truck.repository.TruckRepositoryImpl
import el.dv.data.fayucafinder.network.user.repository.UserRepositoryImpl
import el.dv.domain.fayucafinder.truck.repository.TruckRepository
import el.dv.domain.fayucafinder.user.UserRepository
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
