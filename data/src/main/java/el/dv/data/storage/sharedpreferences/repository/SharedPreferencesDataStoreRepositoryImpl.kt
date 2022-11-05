package el.dv.data.storage.sharedpreferences.repository

import el.dv.data.storage.core.api.DataStoreApi
import el.dv.domain.sharedpreferences.DataStoreRepository

class SharedPreferencesDataStoreRepositoryImpl(
    private val dataStoreApi: DataStoreApi
) : DataStoreRepository {

    override suspend fun saveString(key: String, value: String): Boolean {
        return dataStoreApi.saveString(key, value)
    }

    override suspend fun saveBoolean(key: String, value: Boolean): Boolean {
        return dataStoreApi.saveBoolean(key, value)
    }

    override suspend fun loadString(key: String): String {
        return dataStoreApi.loadString(key)
    }

    override suspend fun loadBoolean(key: String): Boolean {
        return dataStoreApi.loadBoolean(key)
    }

    override suspend fun clearSharedPreferences() {
        dataStoreApi.clearSharedPreferences()
    }
}
