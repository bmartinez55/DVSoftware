package el.dv.fayucafinder.di

import el.dv.domain.fayucafinder.di.fayucaFinderDomainModule
import el.dv.fayucafinderdata.di.FayucaFinderDIDataModuleProvider
import el.dv.presentation.di.presentationModule

object FayucaFinderDIModuleProvider {
    val fayucaFinderModule = listOf(appModule, presentationModule, fayucaFinderDomainModule).plus(FayucaFinderDIDataModuleProvider.getDIDataModule())
}
