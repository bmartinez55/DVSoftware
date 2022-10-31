package el.dv.presentation.view.navigation

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import el.dv.domain.core.Geolocation
import el.dv.domain.logging.AppLog
import el.dv.domain.navigation.NavigationMap
import el.dv.domain.navigation.NavigationMapInteractionListener
import el.dv.domain.navigation.model.AddGeoMarkerRequest
import el.dv.domain.navigation.model.GeoMarker
import el.dv.domain.navigation.model.MapVisualType
import el.dv.domain.navigation.model.NavigationMapInteractionType
import el.dv.domain.navigation.model.RemoveGeoMarkerRequest
import el.dv.domain.navigation.model.UpdateGeoMarkerRequest
import el.dv.presentation.extension.toGeolocation
import el.dv.presentation.extension.toGoogleLatLng
import java.util.function.Predicate

/**
 * Google Maps Implementation
 */
class GoogleNavigationMap(
    private val map: GoogleMap,
    private val context: Context
) : NavigationMap<GoogleMap, Marker> {

    override fun addMarker(addGeoMarkerRequest: AddGeoMarkerRequest): GeoMarker<Marker> {
        AppLog.d(TAG, "addMarker: ${addGeoMarkerRequest.markerType}")
        val markerOptions = MarkerOptions()
            .anchor(ANCHOR_X, ANCHOR_Y)
            .position(addGeoMarkerRequest.location.toGoogleLatLng())
            .title(addGeoMarkerRequest.title)
            .draggable(addGeoMarkerRequest.clickable)

        val marker = requireNotNull(map.addMarker(markerOptions))
        return GeoMarker(
            marker = marker,
            markerType = addGeoMarkerRequest.markerType,
            markerId = addGeoMarkerRequest.markerId
        )
    }

    override fun updateMarker(updateGeoMarkerRequest: UpdateGeoMarkerRequest<Marker>): GeoMarker<Marker> {
        AppLog.d(TAG, "updateMarker")

        updateGeoMarkerRequest.geoMarker.marker.title = updateGeoMarkerRequest.geoMarker.marker.title
        updateGeoMarkerRequest.geoMarker.marker.position = updateGeoMarkerRequest.location.toGoogleLatLng()
        return updateGeoMarkerRequest.geoMarker
    }

    override fun removeMarker(removeGeoMarkerRequest: RemoveGeoMarkerRequest<Marker>): GeoMarker<Marker> {
        AppLog.d(TAG, "removeMarker")
        removeGeoMarkerRequest.geoMarker.marker.remove()
        return removeGeoMarkerRequest.geoMarker
    }

    override fun setZoomLevel(zoomLevel: Float, animate: Boolean) {
        AppLog.d(TAG, "setZoomLevel")
        when (animate) {
            true -> map.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel))
            false -> map.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel))
        }
    }

    override fun setMapCenterLocation(location: Geolocation, zoomLevel: Float, animate: Boolean) {
        AppLog.d(TAG, "setMapCenterLocation")
        val cameraPosition = CameraPosition.builder().target(location.toGoogleLatLng()).zoom(zoomLevel).build()
        when (animate) {
            true -> map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            false -> map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun setMapCenterLocation(coordinateList: List<Geolocation>, boundingBoxPadding: Int, animate: Boolean) {
        AppLog.d(TAG, "setMapCenterLocation by Bounds")
        val latLongBounds = LatLngBounds.builder().also { latLngBuilder ->
            coordinateList.forEach { latLngBuilder.include(it.toGoogleLatLng()) }
        }.build()
        map.setOnMapLoadedCallback {
            when (animate) {
                true -> map.animateCamera(CameraUpdateFactory.newLatLngBounds(latLongBounds, boundingBoxPadding))
                false -> map.moveCamera(CameraUpdateFactory.newLatLngBounds(latLongBounds, boundingBoxPadding))
            }
        }
    }

    override fun setZoomGestureEnabled(enable: Boolean) {
        AppLog.d(TAG, "setZoomGestureEnabled $enable")
        map.uiSettings.isZoomGesturesEnabled = enable
    }

    override fun setRotationGestureEnabled(enable: Boolean) {
        AppLog.d(TAG, "setRotationGestureEnabled $enable")
        map.uiSettings.isRotateGesturesEnabled = enable
    }

    override fun setTiltGestureEnabled(enable: Boolean) {
        AppLog.d(TAG, "setTiltGestureEnabled $enable")
        map.uiSettings.isTiltGesturesEnabled = enable
    }

    override fun setBuildingEnabled(enable: Boolean) {
        AppLog.d(TAG, "setBuildingEnabled $enable")
        map.isBuildingsEnabled = enable
    }

    override fun setIndoorEnabled(enable: Boolean) {
        AppLog.d(TAG, "setIndoorEnabled $enable")
        map.uiSettings.isIndoorLevelPickerEnabled = enable
        map.isIndoorEnabled = enable
    }

    override fun setTrafficEnabled(enable: Boolean) {
        AppLog.d(TAG, "setTrafficEnabled $enable")
        map.isTrafficEnabled = enable
    }

    override fun setMapInteractionListener(listener: NavigationMapInteractionListener, interactionFilterLogic: Predicate<NavigationMapInteractionType>) {
        AppLog.d(TAG, "setMapInteractionListener")
        map.setOnCameraMoveStartedListener { reason ->
            AppLog.d(TAG, "onCameraMoveStartedListener $reason")
            reason.toNavigationMapInteractionType(getCenterLocation()).let { mapInteractionType ->
                when (interactionFilterLogic.test(mapInteractionType)) {
                    true -> listener.onMapInteraction(mapInteractionType)
                    else -> {}
                }
            }
        }
    }

    private fun getCenterLocation(): Geolocation {
        return map.projection.visibleRegion.latLngBounds.center.toGeolocation()
    }

    @SuppressLint("MissingPermission")
    override fun setUserLocationEnabled(enable: Boolean) {
        AppLog.d(TAG, "setUserLocationEnabled")
        map.isMyLocationEnabled = enable
        map.uiSettings.isMyLocationButtonEnabled = false
    }

    override fun setMapToolbarEnabled(enable: Boolean) {
        AppLog.d(TAG, "setMapToolbarEnabled")
        map.uiSettings.isMapToolbarEnabled = enable
    }

    override fun setMapType(mapVisualType: MapVisualType) {
        AppLog.d(TAG, "setMapType $mapVisualType")
        when (mapVisualType) {
            MapVisualType.Normal -> map.mapType = GoogleMap.MAP_TYPE_NORMAL
            MapVisualType.Hybrid -> map.mapType = GoogleMap.MAP_TYPE_HYBRID
            MapVisualType.Satellite -> map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            MapVisualType.Terrain -> map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        AppLog.d(TAG, "setPadding")
        map.setPadding(left, top, right, bottom)
    }

    companion object {
        const val TAG = "GoogleNavigationMap"
        const val ANCHOR_X = 0.5f
        const val ANCHOR_Y = 0.5f
    }
}

fun Int.toNavigationMapInteractionType(centerLocation: Geolocation): NavigationMapInteractionType {
    return when (this) {
        GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> NavigationMapInteractionType.Gesture(centerLocation)
        GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION -> NavigationMapInteractionType.Api
        GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION -> NavigationMapInteractionType.Dev(centerLocation)
        else -> NavigationMapInteractionType.Gesture()
    }
}
