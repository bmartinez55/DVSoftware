package el.dv.presentation.permission.usecase

import el.dv.domain.core.SuspendUseCase
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionApi

class RequestForPermissionGrantedUseCase(private val permissionApi: PermissionApi) : SuspendUseCase<Permission, Unit> {

    override suspend fun run(param: Permission) {
        permissionApi.requestForPermission(permission = param)
    }
}
