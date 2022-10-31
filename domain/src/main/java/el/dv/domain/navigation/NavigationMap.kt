package el.dv.domain.navigation

import el.dv.domain.core.Geolocation
import el.dv.domain.navigation.model.AddGeoMarkerRequest
import el.dv.domain.navigation.model.GeoMarker
import el.dv.domain.navigation.model.MapVisualType
import el.dv.domain.navigation.model.NavigationMapInteractionType
import el.dv.domain.navigation.model.RemoveGeoMarkerRequest
import el.dv.domain.navigation.model.UpdateGeoMarkerRequest
import java.util.function.Predicate

interface NavigationMap<MapType, MarkerType> {
    /**
     * Add geo marker and return added marker
     * @param addGeoMarkerRequest
     * @return GeoMarker<MarkerType>
     */
    fun addMarker(addGeoMarkerRequest: AddGeoMarkerRequest): GeoMarker<MarkerType>

    /**
     * Update requested marker
     * @param updateGeoMarkerRequest
     * @return GeoMarker<MarkerType>
     */
    fun updateMarker(updateGeoMarkerRequest: UpdateGeoMarkerRequest<MarkerType>): GeoMarker<MarkerType>

    /**
     * Remove requested marker
     * @param removeGeoMarkerRequest
     * @return GeoMarker<MarkerType>
     */
    fun removeMarker(removeGeoMarkerRequest: RemoveGeoMarkerRequest<MarkerType>): GeoMarker<MarkerType>

    /**
     * Set map zoom level
     * @param zoomLevel
     * @param animate
     */
    fun setZoomLevel(zoomLevel: Float, animate: Boolean = false)

    /**
     * Set map center point
     * @param location
     * @param zoomLevel
     * @param animate
     */
    fun setMapCenterLocation(location: Geolocation, zoomLevel: Float = 14f, animate: Boolean = false)

    /**
     * Set Map center location based on box bounds
     * @param coordinateList
     * @param boundingBoxPadding
     * @param animate
     */
    fun setMapCenterLocation(coordinateList: List<Geolocation>, boundingBoxPadding: Int = 350, animate: Boolean = false)

    /**
     * Set map zoom gesture feature
     * @param enable
     */
    fun setZoomGestureEnabled(enable: Boolean)

    /**
     * Set map rotation gesture feature
     * @param enable
     */
    fun setRotationGestureEnabled(enable: Boolean)

    /**
     * Set Map tilt gesture feature
     * @param enable
     */
    fun setTiltGestureEnabled(enable: Boolean)

    /**
     * Set building drawing feature
     * @param enable
     */
    fun setBuildingEnabled(enable: Boolean)

    /**
     * Set map indoor drawing feature
     * @param enable
     */
    fun setIndoorEnabled(enable: Boolean)

    /**
     * Set map traffic feature
     * @param enable
     */
    fun setTrafficEnabled(enable: Boolean)

    /**
     * Set map interaction listener
     * @param listener
     * @param interactionFilterLogic
     */
    fun setMapInteractionListener(listener: NavigationMapInteractionListener, interactionFilterLogic: Predicate<NavigationMapInteractionType>) {}

    /**
     * Set current location to shown on map feature
     * @param enable
     */
    fun setUserLocationEnabled(enable: Boolean)

    /**
     * Set Map Toolbar feature
     * @param enable
     */
    fun setMapToolbarEnabled(enable: Boolean)

    /**
     * Set Map Type feature
     * @param mapType
     */
    fun setMapType(mapVisualType: MapVisualType)

    /**
     * Set map padding to align map view if map is underneath other views
     */
    fun setPadding(left: Int, top: Int, right: Int, bottom: Int)
}
