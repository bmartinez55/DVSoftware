package el.dv.domain.navigation.model

import el.dv.domain.location.Geolocation

sealed class NavigationMapInteractionType {
    data class Gesture(val centerLocation: Geolocation = Geolocation()) : NavigationMapInteractionType()
    object Api : NavigationMapInteractionType()
    data class Dev(val centerLocation: Geolocation = Geolocation()) : NavigationMapInteractionType()
}
