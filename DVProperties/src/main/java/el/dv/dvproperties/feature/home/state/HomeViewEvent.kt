package el.dv.dvproperties.feature.home.state

sealed class HomeViewEvent {
    object Init : HomeViewEvent()
    data class HorizontalGridOnClick(val propertyId: Int) : HomeViewEvent()
    object AddPropertyItemOnClick : HomeViewEvent()
}
