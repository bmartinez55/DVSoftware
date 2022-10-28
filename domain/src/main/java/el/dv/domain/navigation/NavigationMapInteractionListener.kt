package el.dv.domain.navigation

import el.dv.domain.navigation.model.NavigationMapInteractionType

interface NavigationMapInteractionListener {
    fun onMapInteraction(interactionType: NavigationMapInteractionType)
}
