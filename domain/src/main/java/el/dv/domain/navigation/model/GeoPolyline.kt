package el.dv.domain.navigation.model

import androidx.annotation.ColorRes
import el.dv.domain.location.Geolocation

data class AddGeoPolylineRequest(
    val geoRoute: GeoRoute,
    val polylineWidth: Float,
    @ColorRes val polylineColor: Int,
    val pattern: Pattern = Pattern.Solid,
    val geoPolyline: GeoPolylineType = GeoPolylineType.Default
)

data class UpdateGeoPolylineRequest<PolylineType>(
    val geoPolyline: GeoPolyline<PolylineType>,
    val geoRoute: GeoRoute,
    val polylineWidth: Float,
    @ColorRes val polylineColor: Int
)

data class GeoPolyline<PolylineType>(
    val polyline: PolylineType,
    val geoPolyline: GeoPolylineType = GeoPolylineType.Default
)

data class GeoRoute(
    val coordinateList: List<Geolocation> = emptyList()
)

enum class Pattern {
    Solid,
    Dash
}

enum class GeoPolylineType {
    Default
}
