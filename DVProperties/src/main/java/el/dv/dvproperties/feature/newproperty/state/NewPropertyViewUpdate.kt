package el.dv.dvproperties.feature.newproperty.state

import el.dv.dvproperties.feature.newproperty.view.NewPropertyViewModel
import el.dv.presentation.view.effect.ViewEffect

data class NewPropertyViewUpdate(
    val viewState: NewPropertyViewModel.InternalState,
    val viewEffects: List<ViewEffect> = emptyList()
)
