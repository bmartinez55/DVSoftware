package el.dv.domain.fayucafinder.di

import el.dv.domain.common.commonDomainModule
import el.dv.domain.fayucafinder.truck.usecase.AddTruckUseCase
import el.dv.domain.fayucafinder.truck.usecase.GetTruckDetailsUseCase
import el.dv.domain.fayucafinder.truck.usecase.GetTrucksFromOwnerUseCase
import el.dv.domain.fayucafinder.truck.usecase.GetTrucksUseCase
import el.dv.domain.fayucafinder.user.usecase.AddUserUseCase
import el.dv.domain.fayucafinder.user.usecase.GetUserUseCase
import el.dv.domain.fayucafinder.user.usecase.UpdateUserUseCase
import el.dv.domain.networkmonitor.usecase.StartNetworkConnectivityMonitorUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val fayucaFinderDomainModule = module {

    includes(commonDomainModule)
    singleOf(::StartNetworkConnectivityMonitorUseCase)

    factoryOf(::AddTruckUseCase)

    factoryOf(::GetTruckDetailsUseCase)

    factoryOf(::GetTrucksUseCase)

    factoryOf(::GetTrucksFromOwnerUseCase)

    factoryOf(::AddTruckUseCase)

    factoryOf(::AddUserUseCase)

    factoryOf(::UpdateUserUseCase)

    factoryOf(::GetUserUseCase)
}
