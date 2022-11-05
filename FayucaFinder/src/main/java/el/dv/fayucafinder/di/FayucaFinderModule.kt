package el.dv.fayucafinder.di

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import el.dv.fayucafinder.R
import el.dv.fayucafinder.feature.map.FayucaFinderMapLifecycleObserver
import el.dv.fayucafinder.feature.map.FayucaFinderMapVM
import el.dv.fayucafinder.feature.map.bottomsheet.MapConfigurationVM
import el.dv.fayucafinder.feature.map.bottomsheet.viewreducer.GetMapConfigurationInitViewStateViewReducer
import el.dv.fayucafinder.feature.map.viewreducer.GetNavigationMapCenterLocationUpdateViewReducer
import el.dv.fayucafinder.util.Const
import el.dv.presentation.extension.ActionListener
import el.dv.presentation.sensor.AccelerometerSensor
import el.dv.presentation.sensor.GyroscopeSensor
import el.dv.presentation.view.manager.dialog.DialogManager
import el.dv.presentation.view.manager.dialog.DialogManagerVertical
import el.dv.presentation.view.manager.notification.NotificationManager
import el.dv.presentation.view.manager.notification.NotificationManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    /**
     * ViewModel related dependencies
     */
    viewModel {
        FayucaFinderMapVM(
            getLocationUseCase = get(),
            stopLocationUseCase = get(),
            saveStringInSharedPreferencesUseCase = get(),
            loadStringFromSharedPreferencesUseCase = get(),
            getNavigationMapCenterLocationUpdateViewReducer = get(),
            permissionFactory = get(),
            appDictionary = get(),
            eventBus = get(),
            context = androidContext()
        )
    }

    viewModel {
        MapConfigurationVM(
            getMapConfigurationInitViewStateViewReducer = get(),
            appDictionary = get(),
            eventBus = get()
        )
    }

    /**
     * Lifecycle related dependencies
     */

    factory { (lifecycleOwner: LifecycleOwner, OnResumeAction: ActionListener, OnPauseAction: ActionListener) ->
        FayucaFinderMapLifecycleObserver(lifecycleOwner = lifecycleOwner, onResumeAction = OnResumeAction, onPauseAction = OnPauseAction)
    }

    /**
     * Manager related dependencies
     */

    factory<DialogManager> {
        DialogManagerVertical(themeStyleResId = R.style.Theme_DVSoftware)
    }

    factory<NotificationManager> {
        NotificationManagerImpl(notificationManager = get(), context = androidContext())
    }

    /**
     * Device related dependencies
     */

    single(named("accelerometer")) {
        AccelerometerSensor(context = androidContext())
    }

    single(named("gyroscope")) {
        GyroscopeSensor(context = androidContext())
    }

    single {
        androidContext().getSharedPreferences(Const.COMMON_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    /**
     * ViewReducer related dependencies
     */

    factory {
        GetMapConfigurationInitViewStateViewReducer(appDictionary = get())
    }

    factory {
        GetNavigationMapCenterLocationUpdateViewReducer()
    }
}
