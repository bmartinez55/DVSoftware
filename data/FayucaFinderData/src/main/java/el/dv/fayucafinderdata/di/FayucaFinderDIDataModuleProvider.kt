package el.dv.fayucafinderdata.di

import el.dv.data.di.commonServiceModule
import el.dv.data.di.utilModule
import org.koin.core.module.Module

object FayucaFinderDIDataModuleProvider {
    fun getDIDataModule(): List<Module> {
        return listOf(
            fayucaFinderDataModule,
            fayucaFinderServiceModule,
            utilModule,
            commonServiceModule
        )
    }
}
