package el.dv.fayucafinder.feature.map

import el.dv.presentation.view.state.NavigationMapState

data class FayucaFinderMapState(
    val navigationMapState: NavigationMapState = NavigationMapState.Init
)

data class NavigationViewReadyState(
    val location: ReadyState = ReadyState.NotReady,
    val view: ReadyState = ReadyState.NotReady
) {
    fun isReady() = location is ReadyState.Ready && view is ReadyState.Ready
}

sealed class ReadyState {
    object Ready : ReadyState()
    object NotReady : ReadyState()
}
