package el.dv.fayucafinder.feature.map.bottomsheet.viewreducer

import el.dv.domain.core.DefaultUseCase
import el.dv.domain.navigation.model.MapVisualType
import el.dv.fayucafinder.R
import el.dv.fayucafinder.feature.map.bottomsheet.MapConfigurationState
import el.dv.fayucafinder.feature.map.bottomsheet.MapConfigurationStateUpdate
import el.dv.fayucafinder.feature.map.bottomsheet.MapConfigurationVM
import el.dv.fayucafinder.feature.map.bottomsheet.MapTypeChip
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.util.AppText

class GetMapConfigurationInitViewStateViewReducer(
    private val appDictionary: AppDictionary
) : DefaultUseCase<GetMapConfigurationInitViewStateViewReducer.Request, MapConfigurationStateUpdate> {

    override fun run(request: Request): MapConfigurationStateUpdate {
        val state = request.internalState.copy(
            viewState = request.internalState.viewState.copy(
                mapConfigurationState = MapConfigurationState.Show(
                    mapTypeTitle = appDictionary.toString(AppText.TranslatableText(R.string.map_configuration_title)),
                    currentMapVisualType = request.currentMapVisualType,
                    mapTypeChipList = listOf(
                        MapTypeChip(
                            title = appDictionary.toString(AppText.TranslatableText(R.string.normal)),
                            mapVisualType = MapVisualType.Normal
                        ),
                        MapTypeChip(
                            title = appDictionary.toString(AppText.TranslatableText(R.string.satellite)),
                            mapVisualType = MapVisualType.Satellite
                        ),
                        MapTypeChip(
                            title = appDictionary.toString(AppText.TranslatableText(R.string.terrain)),
                            mapVisualType = MapVisualType.Terrain
                        ),
                        MapTypeChip(
                            title = appDictionary.toString(AppText.TranslatableText(R.string.hybrid)),
                            mapVisualType = MapVisualType.Hybrid
                        ),
                        MapTypeChip(
                            title = appDictionary.toString(AppText.TranslatableText(R.string.three_dimension_mode)),
                            mapVisualType = MapVisualType.ThreeDimension
                        )
                    )
                )
            ),
            currentMapVisualType = request.currentMapVisualType
        )
        return MapConfigurationStateUpdate(state = state)
    }

    data class Request(
        val internalState: MapConfigurationVM.InternalState,
        val currentMapVisualType: MapVisualType
    )
}
