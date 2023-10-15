package el.dv.dvproperties.feature.home

import el.dv.domain.dvproperties.propertydetails.PropertyDetails

data class HomeViewState(
    val horizontalGridState: HorizontalGridState = HorizontalGridState.Hide
)

sealed class HorizontalGridState {
    data class Show(
        val propertyList: List<PropertyDetails>
    ) : HorizontalGridState()
    object Hide : HorizontalGridState()
}
