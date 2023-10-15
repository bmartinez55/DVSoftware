package el.dv.fayucafinder.di

import el.dv.data.di.DIDataModuleProvider
import el.dv.data.di.model.AppType
import el.dv.domain.fayucafinder.di.fayucaFinderDomainModule
import el.dv.presentation.di.presentationModule

object FayucaFinderDIModuleProvider {
    val fayucaFinderModule = listOf(appModule, presentationModule, fayucaFinderDomainModule).plus(DIDataModuleProvider.getDIDataModule(AppType.Fayuca))
}
