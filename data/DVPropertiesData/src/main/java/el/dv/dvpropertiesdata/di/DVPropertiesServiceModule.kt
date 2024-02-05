package el.dv.dvpropertiesdata.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import el.dv.data.storage.core.api.DataStoreApi
import el.dv.data.storage.sharedpreferences.api.SharedPreferencesDataStoreApi
import el.dv.dvpropertiesdata.network.core.ClientInterceptor
import el.dv.dvpropertiesdata.network.geocoding.GeocodingApi
import el.dv.dvpropertiesdata.network.propertydetails.PROPERTY_DETAILS_DB
import el.dv.dvpropertiesdata.network.propertydetails.PropertyDetailsDB
import el.dv.dvpropertiesdata.util.DVPropertiesConst
import el.dv.dvpropertiesdata.util.DVPropertiesConst.GRASS_HOPPER_BASE_URL
import el.dv.dvpropertiesdata.util.ModuleInstanceNames.CLIENT_INTERCEPTOR
import el.dv.dvpropertiesdata.util.ModuleInstanceNames.OKHTTP_CLIENT
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dVPropertiesServiceModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), PropertyDetailsDB::class.java, PROPERTY_DETAILS_DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<PropertyDetailsDB>().getPropertyDetailsDao()
    }

    single {
        androidContext().getSharedPreferences(DVPropertiesConst.SHARED_PREFS_FILE, Context.MODE_PRIVATE)
    }

    singleOf(::SharedPreferencesDataStoreApi) bind DataStoreApi::class

    single {
        Retrofit.Builder()
            .client(get(named(OKHTTP_CLIENT)))
            .baseUrl(GRASS_HOPPER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GeocodingApi::class.java)
    }

    single(named(OKHTTP_CLIENT)) {
        OkHttpClient.Builder()
            .addInterceptor(get(named(CLIENT_INTERCEPTOR)))
            .build()
    }

    singleOf(::ClientInterceptor) {
        named(CLIENT_INTERCEPTOR)
    }
}
