package el.dv.dvproperties.feature.home.state

import android.content.Context
import androidx.fragment.app.Fragment
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionResult

sealed class HomeViewEvent {
    data class Init(val fragment: Fragment, val context: Context, val permission: Permission) : HomeViewEvent()
    object ErrorScreen : HomeViewEvent()
    data class HorizontalGridOnClick(val propertyId: Int) : HomeViewEvent()
    object AddPropertyItemOnClick : HomeViewEvent()
    data class CheckPermission(val permission: Permission) : HomeViewEvent()
    data class RequestPermission(val permission: Permission) : HomeViewEvent()
    data class RequestedPermissionResult(val permissionResult: PermissionResult) : HomeViewEvent()
}
