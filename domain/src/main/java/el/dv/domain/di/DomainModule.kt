package el.dv.domain.di

import el.dv.domain.core.AppCoroutineDispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::AppCoroutineDispatchers)
}
