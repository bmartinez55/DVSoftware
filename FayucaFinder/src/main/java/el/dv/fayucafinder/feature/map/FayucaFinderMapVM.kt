package el.dv.fayucafinder.feature.map

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.logging.AppLog
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionApi
import el.dv.presentation.permission.PermissionFactory
import el.dv.presentation.permission.PermissionResult
import el.dv.presentation.permission.RequestPermissionCallback
import el.dv.presentation.permission.UIPermissionProviderConstructParams
import el.dv.presentation.permission.isAccessFineLocationPermission
import el.dv.presentation.permission.usecase.CheckPermissionGrantedUseCase
import el.dv.presentation.permission.usecase.RequestForPermissionGrantedUseCase
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FayucaFinderMapVM(
    private val permissionFactory: PermissionFactory<UIPermissionProviderConstructParams<RequestPermissionCallback>, PermissionApi, CheckPermissionGrantedUseCase, RequestForPermissionGrantedUseCase>,
    private val appDictionary: AppDictionary,
    private val context: Context
) : ViewModel() {

    private val internalState = MutableLiveData(getDefaultState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<FayucaFinderMapState> = Transformations.distinctUntilChanged(
        internalState.map {
            it.state
        }
    )

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<FayucaFinderMapViewEvent>(Channel.UNLIMITED)

    // Permission instances
    private lateinit var permissionApi: PermissionApi
    private lateinit var checkPermissionGrantedUseCase: CheckPermissionGrantedUseCase
    private lateinit var requestForPermissionGrantedUseCase: RequestForPermissionGrantedUseCase

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e ->
                AppLog.e(TAG, "event error", e)
            }
            .onEach { event ->
                AppLog.d(TAG, "event: $event")
                when (event) {
                    is FayucaFinderMapViewEvent.Init -> handleInit(event)
                    is FayucaFinderMapViewEvent.ViewLoaded -> handleViewLoaded(event)
                    is FayucaFinderMapViewEvent.CheckIfPermissionIsGranted -> handleCheckIfPermissionIsGranted(event)
                    is FayucaFinderMapViewEvent.RequestForPermissionReceived -> handleRequestForPermissionResultReceived(event)
                }
            }
            .launchIn(viewModelScope)
    }

    fun handleEvent(event: FayucaFinderMapViewEvent) {
        eventChannel.trySend(event)
    }

    private fun updateViewState(internalState: InternalState) {
        this.internalState.value = internalState
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        internalViewEffect.value = viewEffect
    }

    private fun sendViewEffect(viewEffectList: List<ViewEffect>) {
        viewEffectList.forEach { viewEffect ->
            internalViewEffect.value = viewEffect
        }
    }

    private fun handleInit(event: FayucaFinderMapViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
        val requestPermissionCallback = RequestPermissionCallback { permissionResult ->
            handleEvent(
                FayucaFinderMapViewEvent.RequestForPermissionReceived(permission = event.permission, permissionResult = permissionResult)
            )
        }

        permissionApi = permissionFactory.getPermissionApi(
            UIPermissionProviderConstructParams(
                requestPermissionCallback = requestPermissionCallback,
                fragment = event.fragment,
                context = context
            )
        )

        checkPermissionGrantedUseCase = permissionFactory.getCheckPermissionGrantedUseCase(permissionApi)
        requestForPermissionGrantedUseCase = permissionFactory.getRequestForPermissionUseCase(permissionApi)

        handleEvent(FayucaFinderMapViewEvent.CheckIfPermissionIsGranted(permission = event.permission))
    }

    private fun handleViewLoaded(event: FayucaFinderMapViewEvent.ViewLoaded) {
        AppLog.d(TAG, "handleViewLoaded")
    }

    private fun requestForPermission(permission: Permission) {
        AppLog.d(TAG, "requestForPermission")
        viewModelScope.launch {
            requestForPermissionGrantedUseCase.run(permission)
        }
    }

    private fun handleCheckIfPermissionIsGranted(event: FayucaFinderMapViewEvent.CheckIfPermissionIsGranted) {
        AppLog.d(TAG, "handleCheckIfPermissionIsGranted")
        viewModelScope.launch {
            when (checkPermissionGrantedUseCase.run(event.permission)) {
                is PermissionResult.Granted -> handlePermissionGranted(event.permission)
                is PermissionResult.Denied -> requestForPermission(event.permission)
            }
        }
    }

    private fun handleRequestForPermissionResultReceived(event: FayucaFinderMapViewEvent.RequestForPermissionReceived) {
        AppLog.d(TAG, "handleRequestForPermissionResultReceived")
        when (event.permissionResult) {
            is PermissionResult.Granted -> handlePermissionGranted(event.permission)
            is PermissionResult.Denied -> handlePermissionDenied(event.permission)
        }
    }

    private fun handlePermissionGranted(permission: Permission) {
        AppLog.d(TAG, "handlePermissionGranted")
        when {
            permission.permissionId.isAccessFineLocationPermission() -> {
                checkIfNotificationPermissionIsNeeded()
            }
        }
    }

    private fun handlePermissionDenied(permission: Permission) {
        AppLog.d(TAG, "handlePermissionDenied")
        when {
            permission.permissionId.isAccessFineLocationPermission() -> {
                checkIfNotificationPermissionIsNeeded()
            }
        }
    }

    private fun checkIfNotificationPermissionIsNeeded() {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                handleEvent(
                    FayucaFinderMapViewEvent.CheckIfPermissionIsGranted(
                        permission = Permission(permissionId = Manifest.permission.POST_NOTIFICATIONS)
                    )
                )
            }
        }
    }

    private fun getDefaultState(): InternalState {
        return InternalState()
    }

    data class InternalState(
        val state: FayucaFinderMapState = FayucaFinderMapState(),
        val navigationViewReadyState: NavigationViewReadyState = NavigationViewReadyState()
    )

    companion object {
        const val TAG = "FayucaFinderMapVM"
    }
}
