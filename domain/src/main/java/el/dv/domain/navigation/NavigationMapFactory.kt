package el.dv.domain.navigation

/**
 * Factory to create Navigation Maps from different Origins i.e. Google Maps, MapBox
 */
interface NavigationMapFactory<MapType, Marker> {
    fun getNavigationMap(map: MapType): NavigationMap<MapType, Marker>
}
