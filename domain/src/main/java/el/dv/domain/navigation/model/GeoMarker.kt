package el.dv.domain.navigation.model

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import el.dv.domain.location.Geolocation

data class AddGeoMarkerRequest(
    val location: Geolocation,
    @DrawableRes val icon: Int = 0,
    val bitmap: Bitmap? = null,
    val title: String = "",
    val description: String = "",
    val markerId: String = "",
    val markerType: GeoMarkerType = GeoMarkerType.Default,
    val clickable: Boolean = false
)

data class UpdateGeoMarkerRequest<MarkerType>(
    val geoMarker: GeoMarker<MarkerType>,
    val location: Geolocation,
    val markerId: String = "",
    val zoomLevel: Float = 0F,
    val animate: Boolean = false,
    val clickable: Boolean = false
)

data class RemoveGeoMarkerRequest<MarkerType>(
    val geoMarker: GeoMarker<MarkerType>,
    val markerId: String = ""
)

/**
 * Adaptable model class to work with different types of markers from maps
 */
data class GeoMarker<MarkerType>(
    val marker: MarkerType,
    val markerType: GeoMarkerType = GeoMarkerType.Default,
    val markerId: String = ""
)

enum class GeoMarkerType {
    Default,
    SelectedLocation
}
