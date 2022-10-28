package el.dv.presentation.view.navigation

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import el.dv.domain.navigation.NavigationMap
import el.dv.domain.navigation.NavigationMapFactory

/**
 * Implementation of Google Map Factory to create an instance
 */
class GoogleMapFactory(
    private val context: Context
) : NavigationMapFactory<GoogleMap, Marker> {

    override fun getNavigationMap(map: GoogleMap): NavigationMap<GoogleMap, Marker> {
        return GoogleNavigationMap(map, context)
    }
}
