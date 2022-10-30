package el.dv.fayucafinder.feature.map

import androidx.fragment.app.Fragment
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionResult

sealed class FayucaFinderMapViewEvent {
    data class Init(val permission: Permission, val fragment: Fragment) : FayucaFinderMapViewEvent()
    object ViewLoaded : FayucaFinderMapViewEvent()
    data class CheckIfPermissionIsGranted(val permission: Permission) : FayucaFinderMapViewEvent()
    data class RequestForPermissionReceived(val permission: Permission, val permissionResult: PermissionResult) : FayucaFinderMapViewEvent()
}
