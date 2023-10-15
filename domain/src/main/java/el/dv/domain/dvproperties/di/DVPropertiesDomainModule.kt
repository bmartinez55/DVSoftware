package el.dv.domain.dvproperties.di

import el.dv.domain.common.commonDomainModule
import el.dv.domain.networkmonitor.usecase.StartNetworkConnectivityMonitorUseCase
import el.dv.domain.dvproperties.propertydetails.usecase.GetAllPropertiesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dvPropertiesDomainModule = module {
    includes(commonDomainModule)

    singleOf(::StartNetworkConnectivityMonitorUseCase)

    factoryOf(::GetAllPropertiesUseCase)
}
