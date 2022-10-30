package el.dv.presentation.permission

import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import el.dv.presentation.permission.usecase.CheckPermissionGrantedUseCase
import el.dv.presentation.permission.usecase.RequestForPermissionGrantedUseCase

interface PermissionFactory<
    PermissionProviderConstructParams,
    PermissionApi,
    CheckPermissionGrantedUseCase,
    RequestForPermissionUseCase> {

    /**
     * Create permission provider with permissionProviderConstructParams
     */
    fun getPermissionApi(permissionProviderConstructParams: PermissionProviderConstructParams): PermissionApi

    /**
     * Create instance for CheckPermissionGrantedUseCase
     */
    fun getCheckPermissionGrantedUseCase(permissionApi: PermissionApi): CheckPermissionGrantedUseCase

    /**
     * Create instance for RequestForPermissionUseCase
     */
    fun getRequestForPermissionUseCase(permissionApi: PermissionApi): RequestForPermissionUseCase
}

data class UIPermissionProviderConstructParams<PermissionCallback>(
    val requestPermissionCallback: PermissionCallback,
    val fragment: Fragment,
    val context: Context
)

class PermissionFactoryImpl(private val activityResultContracts: ActivityResultContracts.RequestPermission) :
    PermissionFactory<
        UIPermissionProviderConstructParams<RequestPermissionCallback>,
        PermissionApi,
        CheckPermissionGrantedUseCase,
        RequestForPermissionGrantedUseCase> {

    override fun getPermissionApi(
        permissionProviderConstructParams: UIPermissionProviderConstructParams<RequestPermissionCallback>
    ): PermissionApi {
        return PermissionApiImpl(
            activityResultContracts = activityResultContracts,
            requestPermissionCallback = permissionProviderConstructParams.requestPermissionCallback,
            fragment = permissionProviderConstructParams.fragment,
            context = permissionProviderConstructParams.context
        )
    }

    override fun getCheckPermissionGrantedUseCase(permissionApi: PermissionApi): CheckPermissionGrantedUseCase {
        return CheckPermissionGrantedUseCase(permissionApi = permissionApi)
    }

    override fun getRequestForPermissionUseCase(permissionApi: PermissionApi): RequestForPermissionGrantedUseCase {
        return RequestForPermissionGrantedUseCase(permissionApi = permissionApi)
    }
}
