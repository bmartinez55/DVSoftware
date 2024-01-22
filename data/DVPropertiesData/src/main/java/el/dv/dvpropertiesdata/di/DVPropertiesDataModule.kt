package el.dv.dvpropertiesdata.di

import el.dv.data.storage.sharedpreferences.repository.SharedPreferencesDataStoreRepositoryImpl
import el.dv.domain.dvproperties.propertydetails.PropertyDetailsRepository
import el.dv.domain.sharedpreferences.DataStoreRepository
import el.dv.dvpropertiesdata.network.PropertyDetailsRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dVPropertiesDataModule = module {
    singleOf(::PropertyDetailsRepositoryImpl) bind PropertyDetailsRepository::class
    singleOf(::SharedPreferencesDataStoreRepositoryImpl) bind DataStoreRepository::class
}
