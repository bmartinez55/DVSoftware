package el.dv.presentation.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import el.dv.domain.logging.AppLog

class PermissionApiImpl(
    activityResultContracts: ActivityResultContracts.RequestPermission,
    private val requestPermissionCallback: RequestPermissionCallback,
    fragment: Fragment,
    private val context: Context
) : PermissionApi {

    /**
     * register ActivityResultContracts upon fragment creation
     */
    private val activityResultLauncher = fragment.registerForActivityResult(activityResultContracts) { isGranted ->
        when (isGranted) {
            true -> requestPermissionCallback.handlePermissionResult(PermissionResult.Granted(permission = permission))
            false -> requestPermissionCallback.handlePermissionResult(PermissionResult.Denied(permission = permission))
        }
    }

    private var permission = Permission()

    override fun isPermissionGranted(permission: Permission): PermissionResult {
        AppLog.d(TAG, "isPermissionGranted")
        this.permission = permission
        return try {
            when (ContextCompat.checkSelfPermission(context, permission.permissionId)) {
                PackageManager.PERMISSION_GRANTED -> PermissionResult.Granted(permission = permission)
                PackageManager.PERMISSION_DENIED -> PermissionResult.Denied(permission = permission)
                else -> PermissionResult.Denied(permission = permission)
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "isPermissionGranted error", e)
            PermissionResult.Denied(permission = permission)
        }
    }

    override fun requestForPermission(permission: Permission) {
        this.permission = permission
        activityResultLauncher.launch(permission.permissionId)
    }

    companion object {
        const val TAG = "PermissionApiImpl"
    }
}
