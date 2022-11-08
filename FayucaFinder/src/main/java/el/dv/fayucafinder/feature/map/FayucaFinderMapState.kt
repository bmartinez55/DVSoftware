package el.dv.fayucafinder.feature.map

import androidx.annotation.DrawableRes
import el.dv.presentation.view.state.NavigationMapState

data class FayucaFinderMapState(
    val navigationMapState: NavigationMapState = NavigationMapState.Init,
    val currentLocationMenuState: CurrentLocationMenuState = CurrentLocationMenuState.Hide,
    val mapConfigurationMenuState: MapConfigurationMenuState = MapConfigurationMenuState.Hide,
    val bottomBannerViewState: BottomBannerViewState = BottomBannerViewState.Hide
)

data class NavigationViewReadyState(
    val location: ReadyState = ReadyState.NotReady,
    val view: ReadyState = ReadyState.NotReady
) {
    fun isReady() = location is ReadyState.Ready && view is ReadyState.Ready
    fun isLocationNotReady() = location is ReadyState.NotReady
}

enum class NavigationReadyRequirement {
    Location,
    View
}

sealed class ReadyState {
    object Ready : ReadyState()
    object NotReady : ReadyState()
}

sealed class CurrentLocationMenuState {
    object Hide : CurrentLocationMenuState()
    object Show : CurrentLocationMenuState()
}

sealed class MapConfigurationMenuState {
    object Hide : MapConfigurationMenuState()
    object Show : MapConfigurationMenuState()
}

sealed class BottomBannerViewState {
    object Hide : BottomBannerViewState()
    data class ShowWifiDisconnected(@DrawableRes val drawableRes: Int, val title: String) : BottomBannerViewState()
}
