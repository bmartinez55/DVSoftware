package el.dv.presentation.extension

import com.google.android.gms.maps.model.LatLng
import el.dv.domain.location.Geolocation

fun Geolocation.toGoogleLatLng(): LatLng {
    return LatLng(this.lat, this.lon)
}

fun LatLng.toGeolocation(): Geolocation {
    return Geolocation(lat = this.latitude, lon = this.longitude)
}
