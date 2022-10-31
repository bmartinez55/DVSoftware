package el.dv.presentation.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import el.dv.domain.location.LocationManager
import el.dv.domain.location.LocationProvider
import el.dv.domain.location.LocationState
import el.dv.domain.logging.AppLog
import el.dv.presentation.extension.offerWhenOpen
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import android.location.LocationManager as SystemLocationManager

class LocationManagerImpl(
    private val locationProvider: LocationProvider,
    private val locationManager: SystemLocationManager,
    private val context: Context
) : LocationManager {

    override suspend fun getLocation(): Flow<LocationState> = callbackFlow {
        try {
            when (getLocationPermissionState()) {
                LocationState.LocationPermissionGranted -> {
                    // Check current location service state first
                    offerWhenOpen(checkLocationServiceState())
                    locationProvider.getLocation()
                        .catch { e ->
                            AppLog.e(TAG, "getLocation flow exception", e)
                            offerWhenOpen(getLocationPermissionState())
                        }
                        .collectLatest { locationState ->
                            when (locationState) {
                                is LocationState.LocationError -> when (getLocationPermissionState()) {
                                    is LocationState.LocationPermissionDenied -> offerWhenOpen(LocationState.LocationPermissionDenied)
                                    else -> offerWhenOpen(locationState)
                                }
                                else -> offerWhenOpen(locationState)
                            }
                        }
                }
                else -> {}
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "getLocation exception", e)
            offerWhenOpen(getLocationPermissionState())
        }
        awaitClose {
            close()
        }
    }

    override fun stopLocation() {
        locationProvider.stopLocation()
    }

    private fun getLocationPermissionState(): LocationState {
        return when (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PackageManager.PERMISSION_GRANTED -> LocationState.LocationPermissionGranted
            else -> LocationState.LocationPermissionDenied
        }
    }

    private fun checkLocationServiceState(): LocationState {
        return when (isLocationServiceEnabled()) {
            true -> LocationState.LocationEnabled
            false -> LocationState.LocationDisabled
        }
    }

    private fun isLocationServiceEnabled(): Boolean {
        return locationManager.isLocationEnabled ||
            (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER))
    }

    companion object {
        const val TAG = "LocationManagerImpl"
    }
}
