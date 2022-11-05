package el.dv.domain.sharedpreferences.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.domain.sharedpreferences.DataStoreRepository
import el.dv.domain.sharedpreferences.model.LoadValueRequest

class LoadStringFromSharedPreferencesUseCase(private val dataStoreRepository: DataStoreRepository) : SuspendUseCase<LoadValueRequest, String> {

    override suspend fun run(param: LoadValueRequest): String {
        return dataStoreRepository.loadString(param.key)
    }
}

class LoadBooleanFromSharedPreferencesUseCase(private val dataStoreRepository: DataStoreRepository) : SuspendUseCase<LoadValueRequest, Boolean> {

    override suspend fun run(param: LoadValueRequest): Boolean {
        return dataStoreRepository.loadBoolean(param.key)
    }
}
