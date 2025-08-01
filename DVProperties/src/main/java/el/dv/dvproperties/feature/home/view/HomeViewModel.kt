package el.dv.dvproperties.feature.home.view

import android.Manifest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.core.ifFailure
import el.dv.domain.core.ifSuccess
import el.dv.domain.dvproperties.propertydetails.usecase.GetAllOwnedPropertiesUseCase
import el.dv.domain.logging.AppLog
import el.dv.domain.sharedpreferences.model.SaveBooleanRequest
import el.dv.domain.sharedpreferences.usecase.SaveBooleanInSharedPreferencesUseCase
import el.dv.dvproperties.feature.home.state.HomeState
import el.dv.dvproperties.feature.home.state.HomeViewEvent
import el.dv.dvproperties.feature.home.state.HomeViewState
import el.dv.dvpropertiesdata.util.DVPropertiesConst
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionApi
import el.dv.presentation.permission.PermissionFactory
import el.dv.presentation.permission.PermissionResult
import el.dv.presentation.permission.RequestPermissionCallback
import el.dv.presentation.permission.UIPermissionProviderConstructParams
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

class HomeViewModel(
    private val getAllOwnedPropertiesUseCase: GetAllOwnedPropertiesUseCase,
    private val saveBooleanInSharedPreferencesUseCase: SaveBooleanInSharedPreferencesUseCase,
    private val permissionFactory: PermissionFactory<UIPermissionProviderConstructParams<RequestPermissionCallback>, PermissionApi, CheckPermissionGrantedUseCase, RequestForPermissionGrantedUseCase>,
    private val appDictionary: AppDictionary
) : ViewModel() {

    private val internalState = MutableLiveData(InternalState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<HomeViewState> = internalState.map { it.viewState }.distinctUntilChanged()

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<HomeViewEvent>(Channel.UNLIMITED)

    // Permission instances
    private lateinit var permissionApi: PermissionApi
    private lateinit var checkPermissionGrantedUseCase: CheckPermissionGrantedUseCase
    private lateinit var requestForPermissionGrantedUseCase: RequestForPermissionGrantedUseCase

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e -> AppLog.d("Event catch $e") }
            .onEach { event ->
                when (event) {
                    is HomeViewEvent.Init -> handleInit(event)
                    is HomeViewEvent.HorizontalGridOnClick -> handleHorizontalGridOnClick(event)
                    is HomeViewEvent.AddPropertyItemOnClick -> handleAddPropertyItemOnClick(event)
                    is HomeViewEvent.CheckPermission -> handleCheckPermission(event)
                    is HomeViewEvent.RequestPermission -> handleRequestPermission(event)
                    is HomeViewEvent.RequestedPermissionResult -> handleRequestedPermissionResult(event)
                    is HomeViewEvent.ErrorScreen -> handleErrorScreen(event)
                }
            }
            .launchIn(viewModelScope)
    }

    fun handleEvent(event: HomeViewEvent) {
        AppLog.d(TAG, "HomeViewEvent $event")
        eventChannel.trySend(event)
    }

    private fun updateViewState(state: InternalState) {
        AppLog.d(TAG, "updateViewState")
        internalState.value = state
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        AppLog.d(TAG, "sendViewEffect")
        internalViewEffect.value = viewEffect
    }

    private fun handleInit(event: HomeViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
        val requestPermissionCallback = RequestPermissionCallback { permissionResult ->
            handleEvent(HomeViewEvent.RequestedPermissionResult(permissionResult))
        }

        permissionApi = permissionFactory.getPermissionApi(
            UIPermissionProviderConstructParams(
                requestPermissionCallback = requestPermissionCallback,
                fragment = event.fragment,
                context = event.context
            )
        )

        checkPermissionGrantedUseCase = permissionFactory.getCheckPermissionGrantedUseCase(permissionApi)
        requestForPermissionGrantedUseCase = permissionFactory.getRequestForPermissionUseCase(permissionApi)

        handleEvent(HomeViewEvent.CheckPermission(event.permission))
        showInitView()
    }

    private fun showInitView() {
        updateViewState(state = state.copy(viewState = state.viewState.copy(homeState = HomeState.Loading)))
        viewModelScope.launch {
            getAllOwnedPropertiesUseCase.run(Unit).ifSuccess { propertyList ->
                updateViewState(
                    state.copy(viewState = state.viewState.copy(homeState = HomeState.Show(propertyList)))
                )
            }.ifFailure { handleEvent(HomeViewEvent.ErrorScreen) }
        }
    }

    private fun handleErrorScreen(event: HomeViewEvent.ErrorScreen) {
        AppLog.d(TAG, "handleErrorScreen")
        updateViewState(state.copy(viewState = state.viewState.copy(homeState = HomeState.Error)))
    }

    private fun handleHorizontalGridOnClick(event: HomeViewEvent.HorizontalGridOnClick) {
        AppLog.d(TAG, "handleHorizontalGridOnClick")
    }

    private fun handleAddPropertyItemOnClick(event: HomeViewEvent.AddPropertyItemOnClick) {
        AppLog.d(TAG, "handleAddPropertyItemOnClick")
        sendViewEffect(ViewEffect.NavigateToDirection(HomeFragmentDirections.actionHomeFragmentToNewPropertyFragment()))
    }

    private fun handleCheckPermission(event: HomeViewEvent.CheckPermission) {
        AppLog.d(TAG, "handleCheckPermission")
        viewModelScope.launch {
            when (checkPermissionGrantedUseCase.run(event.permission)) {
                is PermissionResult.Granted -> handlePermissionGranted(event.permission)
                is PermissionResult.Denied -> handleEvent(HomeViewEvent.RequestPermission(event.permission))
            }
        }
    }

    private fun handleRequestPermission(event: HomeViewEvent.RequestPermission) {
        AppLog.d(TAG, "handleRequestPermission")
        viewModelScope.launch {
            requestForPermissionGrantedUseCase.run(event.permission)
        }
    }

    private fun handleRequestedPermissionResult(event: HomeViewEvent.RequestedPermissionResult) {
        AppLog.d(TAG, "handleRequestedPermissionResult")
        when (event.permissionResult) {
            is PermissionResult.Granted -> handlePermissionGranted(event.permissionResult.permission)
            is PermissionResult.Denied -> handlePermissionDenied(event.permissionResult.permission)
        }
    }

    private fun handlePermissionGranted(permission: Permission) {
        AppLog.d(TAG, "handlePermissionGranted $permission")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                permission.isImageAccessPermission() -> {
                    viewModelScope.launch {
                        saveBooleanInSharedPreferencesUseCase.run(SaveBooleanRequest(DVPropertiesConst.IMAGE_ACCESS_PERMISSION_GRANTED, true))
                    }
                }
                permission.isVideoAccessPermission() -> {
                    viewModelScope.launch {
                        saveBooleanInSharedPreferencesUseCase.run(SaveBooleanRequest(DVPropertiesConst.VIDEO_ACCESS_PERMISSION_GRANTED, true))
                    }
                }
            }
        }

        when {
            permission.isPermissionCamera() -> {
                viewModelScope.launch {
                    saveBooleanInSharedPreferencesUseCase.run(SaveBooleanRequest(DVPropertiesConst.CAMERA_PERMISSION_GRANTED, true))
                }
                handleEvent(HomeViewEvent.CheckPermission(Permission(Manifest.permission.RECORD_AUDIO)))
            }
            permission.isPermissionRecordAudio() -> {
                viewModelScope.launch {
                    saveBooleanInSharedPreferencesUseCase.run(SaveBooleanRequest(DVPropertiesConst.RECORD_AUDIO_PERMISSION_GRANTED, true))
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    handleEvent(HomeViewEvent.CheckPermission(Permission(Manifest.permission.READ_MEDIA_IMAGES)))
                } else {
                    handleEvent(HomeViewEvent.CheckPermission(Permission(Manifest.permission.READ_EXTERNAL_STORAGE)))
                }
            }
        }
    }

    private fun handlePermissionDenied(permission: Permission) {
        AppLog.d(TAG, "handlePermissionDenied")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                permission.isImageAccessPermission() -> {
                    handleEvent(HomeViewEvent.CheckPermission(Permission(Manifest.permission.READ_MEDIA_VIDEO)))
                }
                permission.isVideoAccessPermission() -> {
                }
            }
        }
        when {
            permission.isPermissionCamera() -> {
                updateViewState(state.copy(isCameraPermissionGranted = false))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    handleEvent(HomeViewEvent.CheckPermission(Permission(Manifest.permission.READ_MEDIA_IMAGES)))
                }
            }
        }
    }

    data class InternalState(
        val viewState: HomeViewState = HomeViewState(),
        val isCameraPermissionGranted: Boolean = false
    )

    companion object {
        const val TAG = "HomeViewModel"
    }
}
