package el.dv.presentation.view.state

import el.dv.domain.core.Geolocation
import el.dv.domain.navigation.NavigationMapInteractionListener
import el.dv.domain.navigation.model.MapVisualType
import el.dv.domain.navigation.model.NavigationMapFeature
import el.dv.domain.navigation.model.NavigationMapInteractionType
import java.util.function.Predicate

sealed class NavigationMapState {
    object Init : NavigationMapState()
    object Hide : NavigationMapState()
    object Idle : NavigationMapState()

    data class Show(
        val scrollPair: Pair<Float, Float> = Pair(0.0f, 0.0f),
        val mapFeature: NavigationMapFeature,
        val mapVisualType: MapVisualType = MapVisualType.Normal,
        val interactionFilterLogic: Predicate<NavigationMapInteractionType>,
        val interactionListener: NavigationMapInteractionListener,
        val navigationMapCenter: NavigationMapCenter = NavigationMapCenter.Unbounded(Geolocation())
    ) : NavigationMapState()

    data class UpdateCenterLocation(
        val navigationMapCenter: NavigationMapCenter,
        val showCurrentLocation: Boolean = false
    ) : NavigationMapState()
}

sealed class NavigationMapCenter {
    data class Unbounded(
        val centerLocation: Geolocation,
        val zoomLevel: Float = 10.0f,
        val animate: Boolean = false,
        val tilt: Float = 0f
    ) : NavigationMapCenter()

    data class Bounded(
        val coordinateList: List<Geolocation>,
        val boundBoxPadding: Int,
        val animate: Boolean = false
    ) : NavigationMapCenter()
}
