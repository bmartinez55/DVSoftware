package el.dv.fayucafinder.di

import androidx.lifecycle.LifecycleOwner
import el.dv.fayucafinder.R
import el.dv.fayucafinder.feature.map.FayucaFinderMapLifecycleObserver
import el.dv.fayucafinder.feature.map.FayucaFinderMapVM
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
    viewModel {
        FayucaFinderMapVM(
            getLocationUseCase = get(),
            stopLocationUseCase = get(),
            permissionFactory = get(),
            appDictionary = get(),
            context = androidContext()
        )
    }

    factory { (lifecycleOwner: LifecycleOwner, OnResumeAction: ActionListener, OnPauseAction: ActionListener) ->
        FayucaFinderMapLifecycleObserver(lifecycleOwner = lifecycleOwner, onResumeAction = OnResumeAction, onPauseAction = OnPauseAction)
    }

    factory<DialogManager> {
        DialogManagerVertical(themeStyleResId = R.style.Theme_DVSoftware)
    }

    factory<NotificationManager> {
        NotificationManagerImpl(notificationManager = get(), context = androidContext())
    }

    single(named("accelerometer")) {
        AccelerometerSensor(context = androidContext())
    }

    single(named("gyroscope")) {
        GyroscopeSensor(context = androidContext())
    }
}
