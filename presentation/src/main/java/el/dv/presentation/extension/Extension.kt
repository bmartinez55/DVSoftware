package el.dv.presentation.extension

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import el.dv.domain.location.Geolocation

typealias OnAction = () -> Unit

fun Geolocation.toGoogleLatLng(): LatLng {
    return LatLng(this.lat, this.lon)
}

fun LatLng.toGeolocation(): Geolocation {
    return Geolocation(lat = this.latitude, lon = this.longitude)
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
