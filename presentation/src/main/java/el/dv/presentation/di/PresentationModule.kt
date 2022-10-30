package el.dv.presentation.di

import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import el.dv.domain.logging.Logger
import el.dv.domain.navigation.NavigationMapFactory
import el.dv.presentation.logging.LoggerImpl
import el.dv.presentation.permission.PermissionApi
import el.dv.presentation.permission.PermissionFactory
import el.dv.presentation.permission.PermissionFactoryImpl
import el.dv.presentation.permission.RequestPermissionCallback
import el.dv.presentation.permission.UIPermissionProviderConstructParams
import el.dv.presentation.permission.usecase.CheckPermissionGrantedUseCase
import el.dv.presentation.permission.usecase.RequestForPermissionGrantedUseCase
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

    factory {
        ActivityResultContracts.RequestPermission()
    }

    single<PermissionFactory<UIPermissionProviderConstructParams<RequestPermissionCallback>, PermissionApi, CheckPermissionGrantedUseCase, RequestForPermissionGrantedUseCase>> {
        PermissionFactoryImpl(activityResultContracts = get())
    }

    factory {
        CheckPermissionGrantedUseCase(permissionApi = get())
    }

    factory {
        RequestForPermissionGrantedUseCase(permissionApi = get())
    }
}
