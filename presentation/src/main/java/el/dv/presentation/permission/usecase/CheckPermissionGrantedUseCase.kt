package el.dv.presentation.permission.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionApi
import el.dv.presentation.permission.PermissionResult

class CheckPermissionGrantedUseCase(private val permissionApi: PermissionApi) : SuspendUseCase<Permission, PermissionResult> {

    override suspend fun run(param: Permission): PermissionResult {
        return permissionApi.isPermissionGranted(permission = param)
    }
}
