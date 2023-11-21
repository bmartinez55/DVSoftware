package el.dv.dvproperties.feature.home.view

import el.dv.domain.dvproperties.propertydetails.model.PropertyDetails

data class HomeViewState(
    val homeState: HomeState = HomeState.Hide
)
sealed class HomeState {
    object Loading : HomeState()
    object Error : HomeState()
    object Hide : HomeState()
    data class Show(
        val propertyList: List<PropertyDetails>
    ) : HomeState()
}

sealed class HorizontalGridState {
    data class Show(
        val propertyList: List<PropertyDetails>
    ) : HorizontalGridState()
    object NoItems : HorizontalGridState()
    object Hide : HorizontalGridState()
}
