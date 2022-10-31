package el.dv.domain.navigation.model

import el.dv.domain.core.Geolocation

sealed class NavigationMapInteractionType {
    data class Gesture(val centerLocation: Geolocation = Geolocation()) : NavigationMapInteractionType()
    object Rotate : NavigationMapInteractionType()
    object Api : NavigationMapInteractionType()
    data class Dev(val centerLocation: Geolocation = Geolocation()) : NavigationMapInteractionType()
}
