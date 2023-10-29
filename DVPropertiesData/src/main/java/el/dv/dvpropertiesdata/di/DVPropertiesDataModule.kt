package el.dv.dvpropertiesdata.di

import el.dv.dvpropertiesdata.network.PropertyDetailsRepositoryImpl
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dVPropertiesDataModule = module {
    singleOf(::PropertyDetailsRepositoryImpl) bind PropertyDetailsRepository::class
}
