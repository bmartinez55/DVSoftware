package el.dv.dvproperties.di

import el.dv.data.di.commonServiceModule
import el.dv.data.di.utilModule
import el.dv.domain.common.commonDomainModule
import el.dv.domain.dvproperties.di.dvPropertiesDomainModule
import el.dv.dvpropertiesdata.di.dVPropertiesDataModule
import el.dv.dvpropertiesdata.di.dVPropertiesServiceModule
import el.dv.presentation.di.presentationModule

object DVPropertiesDIModuleProvider {
    val dvPropertiesModuleList = listOf(
        dvPropertiesModule,
        dvPropertiesDomainModule,
        presentationModule,
        dVPropertiesServiceModule,
        dVPropertiesDataModule,
        commonDomainModule,
        commonServiceModule,
        utilModule
    )
}
