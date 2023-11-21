package el.dv.dvproperties.di

import el.dv.dvproperties.core.view.DVPropertiesViewModel
import el.dv.dvproperties.feature.home.view.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val dvPropertiesModule = module {
    viewModelOf(::DVPropertiesViewModel)
    viewModelOf(::HomeViewModel)
}
