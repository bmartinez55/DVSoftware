package el.dv.presentation.view.effect

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import el.dv.domain.navigation.model.AddGeoMarkerRequest
import el.dv.domain.navigation.model.AddGeoPolylineRequest
import el.dv.domain.navigation.model.GeoPolyline
import el.dv.domain.navigation.model.MapVisualType
import el.dv.domain.navigation.model.RemoveGeoMarkerRequest
import el.dv.domain.navigation.model.UpdateGeoMarkerRequest
import el.dv.domain.navigation.model.UpdateGeoPolylineRequest

sealed class ViewEffect {
    object Default : ViewEffect()

    data class ShowDialogEffect(
        val context: Context,
        val title: String,
        val message: String,
        val positiveButtonTitle: String = "",
        val positiveClickListener: DialogInterface.OnClickListener? = null,
        val negativeButtonTitle: String = "",
        val negativeClickListener: DialogInterface.OnClickListener? = null,
        val onKeyListener: DialogInterface.OnKeyListener? = null
    ) : ViewEffect()

    object DismissDialogEffect : ViewEffect()

    data class StartActivityEffect(val intent: Intent) : ViewEffect()

    data class NavigateToGlobalActionEffect(@IdRes val actionId: Int) : ViewEffect()

    data class NavigateToDirection(val navDirections: NavDirections) : ViewEffect()

    data class UpdateMapTypeEffect(val mapVisualType: MapVisualType) : ViewEffect()

    data class ShowMapConfigurationsScreenEffect(val mapVisualType: MapVisualType) : ViewEffect()

    data class TriggerMarkerEffect(val markerEffect: MarkerEffect) : ViewEffect()

    data class TriggerRouteEffect(val routeEffect: RouteEffect) : ViewEffect()
}

sealed class MarkerEffect {
    data class Add(val addGeoMarkerRequest: AddGeoMarkerRequest) : MarkerEffect()

    data class Remove(val removeGeoMarkerRequest: RemoveGeoMarkerRequest<Marker>) : MarkerEffect()

    data class Update(val updateGeoMarkerRequest: UpdateGeoMarkerRequest<Marker>) : MarkerEffect()
}

sealed class RouteEffect {
    data class Add(val addGeoPolylineRequest: AddGeoPolylineRequest) : RouteEffect()

    data class Remove(val geoPolyline: GeoPolyline<Polyline>) : RouteEffect()

    data class Update(val updateGeoPolylineRequest: UpdateGeoPolylineRequest<Polyline>) : RouteEffect()
}
