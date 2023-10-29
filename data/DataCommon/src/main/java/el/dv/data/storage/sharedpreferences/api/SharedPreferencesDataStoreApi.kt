package el.dv.data.storage.sharedpreferences.api

import android.content.SharedPreferences
import androidx.core.content.edit
import el.dv.data.storage.core.api.DataStoreApi
import el.dv.domain.core.CoroutineDispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesDataStoreApi(
    private val sharedPref: SharedPreferences,
    private val dispatchers: CoroutineDispatchers
) : DataStoreApi {

    /**
     * Saves a string to shared preferences
     * @param key
     * @param value
     * @return [Boolean] success or failure
     */
    override suspend fun saveString(key: String, value: String): Boolean = withContext(dispatchers.IO) {
        sharedPref.edit {
            putString(key, value).apply()
            commit()
        }
        true
    }

    /**
     * Saves a boolean to shared preferences
     * @param key
     * @param value
     * @return [Boolean] success or failure
     */
    override suspend fun saveBoolean(key: String, value: Boolean): Boolean = withContext(dispatchers.IO) {
        sharedPref.edit {
            putBoolean(key, value).apply()
            commit()
        }
        true
    }

    /**
     * Load a string from shared preferences
     * @param key
     * @return [Boolean] success or failure
     */
    override suspend fun loadString(key: String): String = withContext(dispatchers.IO) {
        requireNotNull(sharedPref.getString(key, ""))
    }

    /**
     * Load a boolean from shared preferences
     * @param key
     * @return [Boolean] success or failure
     */
    override suspend fun loadBoolean(key: String): Boolean = withContext(dispatchers.IO) {
        requireNotNull(sharedPref.getBoolean(key, false))
    }

    /**
     * Clear all shared preferences
     */
    override suspend fun clearSharedPreferences() {
        sharedPref.edit().clear().apply()
    }
}
