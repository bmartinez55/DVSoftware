package el.dv.domain.navigation.model

data class NavigationMapFeature(
    val zoomGestureEnabled: Boolean = false,
    val rotationGestureEnabled: Boolean = false,
    val tiltGestureEnabled: Boolean = false,
    val buildingEnabled: Boolean = false,
    val indoorEnabled: Boolean = false,
    val trafficEnabled: Boolean = false,
    val userLocationEnabled: Boolean = false,
    val mapToolbarEnabled: Boolean = false
)
