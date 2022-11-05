package el.dv.fayucafinder.feature.map

import androidx.fragment.app.Fragment
import el.dv.domain.navigation.model.NavigationMapInteractionType
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionResult

sealed class FayucaFinderMapViewEvent {
    data class Init(val permission: Permission, val fragment: Fragment) : FayucaFinderMapViewEvent()
    object ViewLoaded : FayucaFinderMapViewEvent()
    data class CheckIfPermissionIsGranted(val permission: Permission) : FayucaFinderMapViewEvent()
    data class RequestForPermissionReceived(val permission: Permission, val permissionResult: PermissionResult) : FayucaFinderMapViewEvent()
    data class MapInteractedByUser(val interactionType: NavigationMapInteractionType) : FayucaFinderMapViewEvent()
    object CurrentLocationMenuClick : FayucaFinderMapViewEvent()
    object MapConfigurationMenuClick : FayucaFinderMapViewEvent()

    // Lifecycle associated ViewEvents
    object GetLocation : FayucaFinderMapViewEvent()
    object StopLocation : FayucaFinderMapViewEvent()
}
