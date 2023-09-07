package el.dv.presentation.view.effect

import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import el.dv.domain.navigation.model.AddGeoMarkerRequest
import el.dv.domain.navigation.model.AddGeoPolylineRequest
import el.dv.domain.navigation.model.GeoPolyline
import el.dv.domain.navigation.model.RemoveGeoMarkerRequest
import el.dv.domain.navigation.model.UpdateGeoMarkerRequest
import el.dv.domain.navigation.model.UpdateGeoPolylineRequest

inline fun addMarkerEffect(action: () -> AddGeoMarkerRequest): ViewEffect {
    return action.invoke().let {
        ViewEffect.TriggerMarkerEffect(
            MarkerEffect.Add(it)
        )
    }
}

inline fun updateMarkerEffect(action: () -> UpdateGeoMarkerRequest<Marker>): ViewEffect {
    return action.invoke().let {
        ViewEffect.TriggerMarkerEffect(
            MarkerEffect.Update(it)
        )
    }
}

inline fun removeMarkerEffect(action: () -> RemoveGeoMarkerRequest<Marker>): ViewEffect {
    return action.invoke().let {
        ViewEffect.TriggerMarkerEffect(
            MarkerEffect.Remove(it)
        )
    }
}

inline fun addRouteEffect(action: () -> AddGeoPolylineRequest): ViewEffect {
    return action.invoke().let {
        ViewEffect.TriggerRouteEffect(
            RouteEffect.Add(it)
        )
    }
}

inline fun updateRouteEffect(action: () -> UpdateGeoPolylineRequest<Polyline>): ViewEffect {
    return action.invoke().let {
        ViewEffect.TriggerRouteEffect(
            RouteEffect.Update(it)
        )
    }
}

inline fun removeRouteEffect(action: () -> GeoPolyline<Polyline>): ViewEffect {
    return action.invoke().let {
        ViewEffect.TriggerRouteEffect(
            RouteEffect.Remove(it)
        )
    }
}
