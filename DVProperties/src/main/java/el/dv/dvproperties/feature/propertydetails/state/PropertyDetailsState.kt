package el.dv.dvproperties.feature.propertydetails.state

import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails
import el.dv.dvproperties.feature.propertydetails.view.PropertyDetailsViewModel
import el.dv.presentation.view.effect.ViewEffect

data class PropertyDetailsState(
    val propertyDetailsViewState: PropertyDetailsViewState = PropertyDetailsViewState.Hide
)

sealed class PropertyDetailsViewState {
    object Hide : PropertyDetailsViewState()
    data class Show(val propertyDetails: PropertyDetails) : PropertyDetailsViewState()
    object Error : PropertyDetailsViewState()
}

sealed class PropertyDetailsViewEvents {
    data class Init(val propertyId: String) : PropertyDetailsViewEvents()
    object OnBackPress : PropertyDetailsViewEvents()
}

data class PropertyDetailsViewUpdate(
    val state: PropertyDetailsViewModel.InternalState,
    val viewEffectList: List<ViewEffect> = emptyList()
)
