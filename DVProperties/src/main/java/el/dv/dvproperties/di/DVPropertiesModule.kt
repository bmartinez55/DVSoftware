package el.dv.dvproperties.di

import el.dv.dvproperties.core.view.DVPropertiesViewModel
import el.dv.dvproperties.feature.home.view.HomeViewModel
import el.dv.dvproperties.feature.newproperty.NewPropertyViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val dvPropertiesModule = module {
    viewModelOf(::DVPropertiesViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::NewPropertyViewModel)
}
