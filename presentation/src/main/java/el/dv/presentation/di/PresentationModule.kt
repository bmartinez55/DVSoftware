package el.dv.presentation.di

import el.dv.domain.logging.Logger
import el.dv.presentation.logging.LoggerImpl
import org.koin.dsl.module

val presentationModule = module {
    single<Logger> {
        LoggerImpl
    }
}