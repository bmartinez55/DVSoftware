package el.dv.fayucafinderdata.di

import el.dv.domain.fayucafinder.truck.repository.TruckRepository
import el.dv.domain.fayucafinder.user.UserRepository
import el.dv.fayucafinderdata.network.truck.repository.TruckRepositoryImpl
import el.dv.fayucafinderdata.network.user.repository.UserRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * This module will hold all repository services
 */
val fayucaFinderDataModule = module {
    singleOf(::TruckRepositoryImpl) bind TruckRepository::class

    singleOf(::UserRepositoryImpl) bind UserRepository::class
}
