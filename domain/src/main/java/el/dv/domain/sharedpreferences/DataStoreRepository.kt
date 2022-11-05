package el.dv.domain.sharedpreferences

/**
 * Data Store repository for key/value storing in-device
 */
interface DataStoreRepository {

    /**
     * Saves a string to shared preferences
     * @param key
     * @param value
     * @return [Boolean] success or failure
     */
    suspend fun saveString(key: String, value: String): Boolean

    /**
     * Saves a boolean to shared preferences
     * @param key
     * @param value
     * @return [Boolean] success or failure
     */
    suspend fun saveBoolean(key: String, value: Boolean): Boolean

    /**
     * Load a string from shared preferences
     * @param key
     * @return [Boolean] success or failure
     */
    suspend fun loadString(key: String): String

    /**
     * Load a boolean from shared preferences
     * @param key
     * @return [Boolean] success or failure
     */
    suspend fun loadBoolean(key: String): Boolean

    /**
     * Clear all shared preferences
     */
    suspend fun clearSharedPreferences()
}
