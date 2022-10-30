package el.dv.presentation.permission

import android.Manifest

interface PermissionApi {
    fun isPermissionGranted(permission: Permission): PermissionResult
    fun requestForPermission(permission: Permission)
}

data class Permission(val permissionId: String = "")

sealed class PermissionResult {
    data class Granted(val permission: Permission) : PermissionResult()
    data class Denied(val permission: Permission) : PermissionResult()
}

fun interface RequestPermissionCallback {
    fun handlePermissionResult(permissionResult: PermissionResult)
}

fun PermissionResult.isGranted(): Boolean {
    return this is PermissionResult.Granted
}

fun String.isAccessFineLocationPermission(): Boolean {
    return this == Manifest.permission.ACCESS_FINE_LOCATION
}
