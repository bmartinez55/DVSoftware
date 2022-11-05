package el.dv.fayucafinder.feature.map.viewreducer

import el.dv.domain.core.DefaultUseCase
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.feature.map.FayucaFinderMapUpdateState
import el.dv.fayucafinder.feature.map.FayucaFinderMapVM
import el.dv.fayucafinder.util.Const
import el.dv.presentation.view.state.NavigationMapCenter
import el.dv.presentation.view.state.NavigationMapState

class GetNavigationMapCenterLocationUpdateViewReducer :
    DefaultUseCase<GetNavigationMapCenterLocationUpdateViewReducer.Request, FayucaFinderMapUpdateState> {

    override fun run(request: Request): FayucaFinderMapUpdateState {
        val state = when (request.internalState.mapConfigurations.mapVisualType) {
            MapVisualType.ThreeDimension -> request.internalState.copy(
                viewState = request.internalState.viewState.copy(
                    navigationMapState = NavigationMapState.UpdateCenterLocation(
                        navigationMapCenter = NavigationMapCenter.Unbounded(
                            centerLocation = request.internalState.userCurrentLocation,
                            zoomLevel = Const.THREE_DIMENSION_ZOOM_LEVEL,
                            animate = true,
                            tilt = Const.THREE_DIMENSION_CAMERA_TILT
                        ),
                        showCurrentLocation = request.internalState.locationPermissionGranted
                    )
                ),
                mapConfigurations = request.internalState.mapConfigurations.copy(
                    mapFeature = request.internalState.mapConfigurations.mapFeature.copy(buildingEnabled = true)
                )
            )
            else -> request.internalState.copy(
                viewState = request.internalState.viewState.copy(
                    navigationMapState = NavigationMapState.UpdateCenterLocation(
                        navigationMapCenter = NavigationMapCenter.Unbounded(
                            centerLocation = request.internalState.userCurrentLocation,
                            zoomLevel = Const.CURRENT_LOCATION_SELECTION_ZOOM_LEVEL,
                            animate = true,
                            tilt = 0f
                        ),
                        showCurrentLocation = request.internalState.locationPermissionGranted
                    )
                ),
                mapConfigurations = request.internalState.mapConfigurations.copy(
                    mapFeature = request.internalState.mapConfigurations.mapFeature.copy(buildingEnabled = false)
                )
            )
        }
        return FayucaFinderMapUpdateState(state = state)
    }

    data class Request(
        val internalState: FayucaFinderMapVM.InternalState
    )
}
