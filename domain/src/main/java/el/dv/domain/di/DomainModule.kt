package el.dv.domain.di

import el.dv.domain.core.AppCoroutineDispatchers
import el.dv.domain.core.CoroutineDispatchers
import org.koin.dsl.module

val domainModule = module {
    single<CoroutineDispatchers> {
        AppCoroutineDispatchers()
    }
}
