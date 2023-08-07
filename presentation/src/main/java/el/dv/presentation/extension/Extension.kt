package el.dv.presentation.extension

import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import el.dv.domain.core.Geolocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

typealias ActionListener = () -> Unit

fun Geolocation.toGoogleLatLng(): LatLng {
    return LatLng(this.lat, this.lon)
}

fun LatLng.toGeolocation(): Geolocation {
    return Geolocation(lat = this.latitude, lon = this.longitude)
}

fun Location.toGeoLocation(): Geolocation {
    return Geolocation(lat = this.latitude, lon = this.longitude, accuracy = this.accuracy)
}

fun Fragment.onBackPress(enabled: Boolean, onBackPressAction: () -> Unit) {
    this.activity?.onBackPressedDispatcher?.addCallback(
        this.viewLifecycleOwner,
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressAction()
            }
        }
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <E> SendChannel<E>.offerWhenOpen(data: E) {
    when (this.isClosedForSend) {
        false -> this.trySend(data)
        else -> {}
    }
}

fun <K, V> Map<K, V>.addOrUpdate(key: K, value: V): Map<K, V> {
    return this.toMutableMap().apply {
        this[key] = value
    }.toMap()
}

fun <T : Parcelable> T.convertParcelableToBundle(key: String): Bundle {
    return Bundle().apply {
        putParcelable(key, this@convertParcelableToBundle)
    }
}

fun <T : Any> Bundle.getBundleData(key: String, defaultValue: T): T {
    return when (this.containsKey(key)) {
        true -> this.getParcelable(key) ?: defaultValue
        false -> defaultValue
    }
}

fun <T : Any> Fragment.getBundleData(key: String, defaultValue: T) = lazy {
    arguments?.getBundleData(key, defaultValue) ?: defaultValue
}

/**
 * Created a job that has launches a Coroutine Scope periodically according to the delay time.
 * @param delayMillis
 * @param action
 * @return Job
 */
fun CoroutineScope.launchPeriodic(delayMillis: Long, action: CoroutineScope.() -> Unit): Job {
    return launch {
        while (!this.isActive) {
            action()
            delay(delayMillis)
        }
    }
}
