package el.dv.fayucafinder.di

import el.dv.data.di.dataModule
import el.dv.domain.di.domainModule
import el.dv.presentation.di.presentationModule

object FayucaFinderDIModuleProvider {
    val fayucaFinderModule = listOf(appModule, presentationModule, domainModule, dataModule)
}