package el.dv.presentation.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import el.dv.domain.location.LocationProvider
import el.dv.domain.location.LocationState
import el.dv.domain.logging.AppLog
import el.dv.presentation.extension.offerWhenOpen
import el.dv.presentation.extension.toGeoLocation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.time.Duration
import kotlin.coroutines.suspendCoroutine

class LocationProviderImpl(private val context: Context) : LocationProvider {

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL.toMillis()).apply {
        setWaitForAccurateLocation(false)
        setMinUpdateIntervalMillis(LOCATION_UPDATE_FASTEST_INTERVAL.toMillis())
        setMaxUpdateDelayMillis(LOCATION_UPDATE_INTERVAL.toMillis().plus(LOCATION_UPDATE_FASTEST_INTERVAL.toMillis()))
        setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        setDurationMillis(Long.MAX_VALUE)
        setIntervalMillis(LOCATION_UPDATE_INTERVAL.toMillis())
        setMaxUpdates(Int.MAX_VALUE)
    }.build()

    private lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Flow<LocationState> = callbackFlow {
        AppLog.d(TAG, "getLocation")
        try {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        offerWhenOpen(LocationState.LocationUpdate(location = location.toGeoLocation()))
                    }
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                    AppLog.d(TAG, "location availability changed")
                    this@callbackFlow.launch {
                        when (isGoogleLocationSettingEnabled()) {
                            true -> offerWhenOpen(LocationState.LocationEnabled)
                            false -> offerWhenOpen(LocationState.LocationDisabled)
                        }
                    }
                }
            }

            // One shot to get current location quickly to update the view while location callback gets started
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    offerWhenOpen(LocationState.LocationUpdate(location = it.toGeoLocation()))
                }
            }

            // Register LocationCallback to get location updates
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnSuccessListener {
                AppLog.d(TAG, "getLocation success")
            }.addOnFailureListener { e ->
                AppLog.e(TAG, "getLocation failure", e)
                offerWhenOpen(LocationState.LocationError)
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "getLocation exception", e)
            offerWhenOpen(LocationState.LocationDisabled)
        }

        awaitClose {
            stopLocation()
            close()
        }
    }

    override fun stopLocation() {
        AppLog.d(TAG, "stopLocation")
        try {
            if (::locationCallback.isInitialized) {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "stopLocation error", e)
        }
    }

    private suspend fun isGoogleLocationSettingEnabled(): Boolean = suspendCoroutine { continuation ->
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            continuation.resumeWith(Result.success(true))
        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                continuation.resumeWith(Result.success(false))
            }
        }
    }

    companion object {
        const val TAG = "LocationProviderImpl"
        val LOCATION_UPDATE_INTERVAL: Duration = Duration.ofSeconds(1)
        val LOCATION_UPDATE_FASTEST_INTERVAL: Duration = Duration.ofMillis(500)
    }
}
