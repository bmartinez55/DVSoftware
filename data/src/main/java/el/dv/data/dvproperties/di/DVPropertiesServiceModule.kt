package el.dv.data.dvproperties.di

import androidx.room.Room
import el.dv.data.dvproperties.network.PROPERTY_DETAILS_DB
import el.dv.data.dvproperties.network.PropertyDetailsDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dVPropertiesServiceModule = module {
    single {
        Room.databaseBuilder(androidContext(), PropertyDetailsDB::class.java, PROPERTY_DETAILS_DB).build()
    }

    single {
        get<PropertyDetailsDB>().getPropertyDetailsDao()
    }
}
