package el.dv.presentation.uinavigation

import android.os.Bundle
import androidx.navigation.NavDirections

sealed class NavigationEvent {
    data class NavDirectionEvent(
        val navDirections: NavDirections,
        val bundle: Bundle = Bundle(),
        val rootViewChanged: Boolean = false
    ) : NavigationEvent() {
        fun build(): NavDirections {
            return navDirections
        }
    }
}
