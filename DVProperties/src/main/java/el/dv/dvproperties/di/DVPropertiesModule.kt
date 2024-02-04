package el.dv.dvproperties.di

import el.dv.dvproperties.R
import el.dv.dvproperties.core.view.DVPropertiesViewModel
import el.dv.dvproperties.feature.home.view.HomeViewModel
import el.dv.dvproperties.feature.newproperty.view.NewPropertyViewModel
import el.dv.dvproperties.feature.propertydetails.view.viewreducer.GetPropertyDetailsViewReducer
import el.dv.presentation.view.manager.dialog.DialogManager
import el.dv.presentation.view.manager.dialog.DialogManagerVertical
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dvPropertiesModule = module {
    /**
     * Declarations of ViewModels
     */
    viewModelOf(::DVPropertiesViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::NewPropertyViewModel)

    /**
     * Declarations of ViewReducers
     */
    factoryOf(::GetPropertyDetailsViewReducer)

    /**
     * Declarations of Classes
     */
    factory<DialogManager> {
        DialogManagerVertical(R.style.DVProperties_Dialog_Style)
    }
}
