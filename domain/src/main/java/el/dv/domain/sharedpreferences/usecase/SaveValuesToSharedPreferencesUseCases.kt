package el.dv.domain.sharedpreferences.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.domain.sharedpreferences.DataStoreRepository
import el.dv.domain.sharedpreferences.model.SaveBooleanRequest
import el.dv.domain.sharedpreferences.model.SaveStringRequest

class SaveStringInSharedPreferencesUseCase(private val dataStoreRepository: DataStoreRepository) : SuspendUseCase<SaveStringRequest, Boolean> {

    override suspend fun run(param: SaveStringRequest): Boolean {
        return dataStoreRepository.saveString(param.key, param.value)
    }
}

class SaveBooleanInSharedPreferencesUseCase(private val dataStoreRepository: DataStoreRepository) : SuspendUseCase<SaveBooleanRequest, Boolean> {

    override suspend fun run(param: SaveBooleanRequest): Boolean {
        return dataStoreRepository.saveBoolean(param.key, param.value)
    }
}
