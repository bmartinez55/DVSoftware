package el.dv.dvproperties.di

import el.dv.dvproperties.core.view.DVPropertiesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dvPropertiesModule = module {

    viewModel {
        DVPropertiesViewModel()
    }
}
