package el.dv.fayucafinder.feature.map.bottomsheet

import el.dv.domain.navigation.model.MapVisualType

sealed class MapConfigurationViewEvent {
    data class Init(val mapVisualType: MapVisualType) : MapConfigurationViewEvent()
    data class MapVisualTypeButtonClick(val mapVisualType: MapVisualType) : MapConfigurationViewEvent()
    object CloseButtonClick : MapConfigurationViewEvent()
}

data class MapConfigurationViewState(
    val mapConfigurationState: MapConfigurationState = MapConfigurationState.Hide
)

sealed class MapConfigurationState {
    object Hide : MapConfigurationState()
    data class Show(
        val mapTypeTitle: String,
        val currentMapVisualType: MapVisualType,
        val mapTypeChipList: List<MapTypeChip>
    ) : MapConfigurationState()
}

data class MapTypeChip(
    val title: String,
    val mapVisualType: MapVisualType
)
