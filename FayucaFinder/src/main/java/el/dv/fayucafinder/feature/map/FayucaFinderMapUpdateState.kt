package el.dv.fayucafinder.feature.map

import el.dv.presentation.view.effect.ViewEffect

data class FayucaFinderMapUpdateState(
    val state: FayucaFinderMapVM.InternalState,
    val viewEffectList: List<ViewEffect>
)
