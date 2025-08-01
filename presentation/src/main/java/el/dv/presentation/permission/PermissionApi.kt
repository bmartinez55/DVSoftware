package el.dv.presentation.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

interface PermissionApi {
    fun isPermissionGranted(permission: Permission): PermissionResult
    fun requestForPermission(permission: Permission)
}

data class Permission(val permissionId: String = "") {
    fun isPermissionCamera(): Boolean = this.permissionId == Manifest.permission.CAMERA
    fun isPermissionRecordAudio(): Boolean = this.permissionId == Manifest.permission.RECORD_AUDIO

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun isImageAccessPermission(): Boolean = this.permissionId == Manifest.permission.READ_MEDIA_IMAGES

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun isVideoAccessPermission(): Boolean = this.permissionId == Manifest.permission.READ_MEDIA_VIDEO
}

sealed class PermissionResult {
    data class Granted(val permission: Permission) : PermissionResult()
    data class Denied(val permission: Permission) : PermissionResult()
}

data class PermissionResultState(
    val isCameraPermissionGranted: MutableState<Boolean> = mutableStateOf(false),
    val isReadExternalStoragePermissionGranted: MutableState<Boolean> = mutableStateOf(false),
    val isRecordAudioPermissionGranted: MutableState<Boolean> = mutableStateOf(false),
    val isImageAccessPermissionGranted: MutableState<Boolean> = mutableStateOf(false),
    val isVideoAccessPermissionGranted: MutableState<Boolean> = mutableStateOf(false)
)

fun interface RequestPermissionCallback {
    fun handlePermissionResult(permissionResult: PermissionResult)
}

fun PermissionResult.isGranted(): Boolean {
    return this is PermissionResult.Granted
}

fun String.isAccessFineLocationPermission(): Boolean {
    return this == Manifest.permission.ACCESS_FINE_LOCATION
}
