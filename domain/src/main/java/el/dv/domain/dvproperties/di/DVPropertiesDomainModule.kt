package el.dv.domain.dvproperties.di

import el.dv.domain.common.commonDomainModule
import el.dv.domain.dvproperties.propertydetails.usecase.AddNewPropertyUseCase
import el.dv.domain.dvproperties.propertydetails.usecase.GetAllOwnedPropertiesUseCase
import el.dv.domain.dvproperties.propertydetails.usecase.GetOwnedPropertiesByTypeUseCase
import el.dv.domain.networkmonitor.usecase.StartNetworkConnectivityMonitorUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dvPropertiesDomainModule = module {
    includes(commonDomainModule)

    singleOf(::StartNetworkConnectivityMonitorUseCase)

    factoryOf(::GetAllOwnedPropertiesUseCase)

    factoryOf(::GetOwnedPropertiesByTypeUseCase)

    factoryOf(::AddNewPropertyUseCase)
}
