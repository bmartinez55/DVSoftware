package el.dv.presentation.di

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import el.dv.domain.logging.Logger
import el.dv.domain.navigation.NavigationMapFactory
import el.dv.presentation.logging.LoggerImpl
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.util.AppDictionaryImpl
import el.dv.presentation.view.navigation.GoogleMapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val presentationModule = module {
    single<NavigationMapFactory<GoogleMap, Marker>> {
        GoogleMapFactory(androidContext())
    }

    single<Logger> {
        LoggerImpl
    }

    single<CoroutineScope> {
        GlobalScope
    }

    single<AppDictionary> {
        AppDictionaryImpl(androidContext())
    }
}
