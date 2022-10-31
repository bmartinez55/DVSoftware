package el.dv.domain.location

import el.dv.domain.core.Geolocation
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    suspend fun getLocation(): Flow<LocationState>
    fun stopLocation()
}

interface LocationManager {
    suspend fun getLocation(): Flow<LocationState>
    fun stopLocation()
}

sealed class LocationState {
    data class LocationUpdate(val location: Geolocation) : LocationState()
    object LocationPermissionGranted : LocationState()
    object LocationPermissionDenied : LocationState()
    object LocationDisabled : LocationState()
    object LocationEnabled : LocationState()
    object LocationError : LocationState()
}
