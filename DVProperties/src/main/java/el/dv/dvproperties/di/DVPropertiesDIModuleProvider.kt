package el.dv.dvproperties.di

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
        dVPropertiesDataModule
    )
}
