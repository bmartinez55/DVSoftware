package el.dv.fayucafinder.feature.map.bottomsheet

import el.dv.presentation.view.effect.ViewEffect

data class MapConfigurationStateUpdate(
    val state: MapConfigurationVM.InternalState,
    val viewEffect: List<ViewEffect> = emptyList()
)
