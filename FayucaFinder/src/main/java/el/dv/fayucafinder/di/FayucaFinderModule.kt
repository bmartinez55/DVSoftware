package el.dv.fayucafinder.di

import androidx.lifecycle.LifecycleOwner
import el.dv.fayucafinder.R
import el.dv.fayucafinder.feature.map.FayucaFinderMapLifecycleObserver
import el.dv.fayucafinder.feature.map.FayucaFinderMapVM
import el.dv.presentation.extension.OnAction
import el.dv.presentation.view.dialogmanager.DialogManager
import el.dv.presentation.view.dialogmanager.DialogManagerVertical
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        FayucaFinderMapVM(
            appDictionary = get()
        )
    }

    factory { (lifecycleOwner: LifecycleOwner, OnResumeAction: OnAction, OnPauseAction: OnAction) ->
        FayucaFinderMapLifecycleObserver(lifecycleOwner, OnResumeAction, OnPauseAction)
    }

    factory<DialogManager> {
        DialogManagerVertical(R.style.Theme_DVSoftware)
    }
}
