package el.dv.data.di

import el.dv.data.di.model.AppType
import el.dv.data.di.scope.fayucaFinderDataModule
import el.dv.data.di.scope.fayucaFinderServiceModule
import org.koin.core.module.Module

object DIDataModuleProvider {
    fun getDIDataModule(appType: AppType): List<Module> {
        return when (appType) {
            AppType.Fayuca -> listOf(
                fayucaFinderDataModule,
                fayucaFinderServiceModule,
                utilModule
            )
        }
    }
}
