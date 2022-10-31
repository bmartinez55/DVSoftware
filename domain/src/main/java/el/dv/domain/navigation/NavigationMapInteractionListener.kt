package el.dv.domain.navigation

import el.dv.domain.navigation.model.NavigationMapInteractionType

fun interface NavigationMapInteractionListener {
    fun onMapInteraction(interactionType: NavigationMapInteractionType)
}
