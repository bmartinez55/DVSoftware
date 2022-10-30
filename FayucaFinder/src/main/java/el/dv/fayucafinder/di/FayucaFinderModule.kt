package el.dv.fayucafinder.di

import androidx.lifecycle.LifecycleOwner
import el.dv.fayucafinder.R
import el.dv.fayucafinder.feature.map.FayucaFinderMapLifecycleObserver
import el.dv.fayucafinder.feature.map.FayucaFinderMapVM
import el.dv.presentation.extension.OnAction
import el.dv.presentation.view.manager.dialog.DialogManager
import el.dv.presentation.view.manager.dialog.DialogManagerVertical
import el.dv.presentation.view.manager.notification.NotificationManager
import el.dv.presentation.view.manager.notification.NotificationManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        FayucaFinderMapVM(
            permissionFactory = get(),
            appDictionary = get(),
            context = androidContext()
        )
    }

    factory { (lifecycleOwner: LifecycleOwner, OnResumeAction: OnAction, OnPauseAction: OnAction) ->
        FayucaFinderMapLifecycleObserver(lifecycleOwner = lifecycleOwner, onResumeAction = OnResumeAction, onPauseAction = OnPauseAction)
    }

    factory<DialogManager> {
        DialogManagerVertical(themeStyleResId = R.style.Theme_DVSoftware)
    }

    factory<NotificationManager> {
        NotificationManagerImpl(notificationManager = get(), context = androidContext())
    }
}
