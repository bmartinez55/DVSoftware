package el.dv.domain.common

import el.dv.domain.core.AppCoroutineDispatchers
import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.event.EventBus
import el.dv.domain.event.EventBusImpl
import el.dv.domain.sharedpreferences.usecase.LoadBooleanFromSharedPreferencesUseCase
import el.dv.domain.sharedpreferences.usecase.LoadStringFromSharedPreferencesUseCase
import el.dv.domain.sharedpreferences.usecase.SaveBooleanInSharedPreferencesUseCase
import el.dv.domain.sharedpreferences.usecase.SaveStringInSharedPreferencesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonDomainModule = module {
    singleOf(::AppCoroutineDispatchers) bind CoroutineDispatchers::class

    singleOf(::EventBusImpl) bind EventBus::class

    factoryOf(::SaveBooleanInSharedPreferencesUseCase)

    factoryOf(::SaveStringInSharedPreferencesUseCase)

    factoryOf(::LoadBooleanFromSharedPreferencesUseCase)

    factoryOf(::LoadStringFromSharedPreferencesUseCase)
}
