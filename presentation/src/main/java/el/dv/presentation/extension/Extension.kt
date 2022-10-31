package el.dv.presentation.extension

import android.location.Location
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import el.dv.domain.core.Geolocation
import kotlinx.coroutines.channels.SendChannel

typealias OnAction = () -> Unit

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

fun <E> SendChannel<E>.offerWhenOpen(data: E) {
    when (this.isClosedForSend) {
        false -> this.trySend(data)
        else -> {}
    }
}
