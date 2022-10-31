package el.dv.fayucafinder.feature.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.core.Geolocation
import el.dv.domain.location.LocationState
import el.dv.domain.logging.AppLog
import el.dv.domain.navigation.model.MapZoomType
import el.dv.domain.navigation.model.NavigationMapFeature
import el.dv.domain.navigation.model.NavigationMapInteractionType
import el.dv.presentation.location.usecase.GetLocationUseCase
import el.dv.presentation.location.usecase.StopLocationUseCase
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
import el.dv.presentation.util.AppText
import el.dv.presentation.view.effect.ViewEffect
import el.dv.presentation.view.state.NavigationMapCenter
import el.dv.presentation.view.state.NavigationMapState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import el.dv.fayucafinder.R as FayucaFinderRes

class FayucaFinderMapVM(
    private val getLocationUseCase: GetLocationUseCase,
    private val stopLocationUseCase: StopLocationUseCase,
    private val permissionFactory: PermissionFactory<UIPermissionProviderConstructParams<RequestPermissionCallback>, PermissionApi, CheckPermissionGrantedUseCase, RequestForPermissionGrantedUseCase>,
    private val appDictionary: AppDictionary,
    private val context: Context
) : ViewModel() {

    private val internalState = MutableLiveData(getDefaultState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<FayucaFinderMapState> = Transformations.distinctUntilChanged(
        internalState.map {
            it.viewState
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
                    is FayucaFinderMapViewEvent.MapInteractedByUser -> handleMapInteractedByUser(event)
                    is FayucaFinderMapViewEvent.GetLocation -> handleGetLocation(event)
                    is FayucaFinderMapViewEvent.StopLocation -> handleStopLocation(event)
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
        when (state.isReady()) {
            true -> updateViewState(
                state.copy(
                    viewState = state.viewState
                )
            )
            false -> showInitViewState()
        }
    }

    private fun showInitViewState() {
        AppLog.d(TAG, "showInitViewState")
        updateViewState(
            state.copy(
                viewState = state.viewState.copy(
                    navigationMapState = when {
                        state.isReady() -> NavigationMapState.Show(
                            navigationMapCenter = NavigationMapCenter.Unbounded(
                                centerLocation = state.userCurrentLocation,
                                zoomLevel = MAP_ZOOM_LEVEL,
                                animate = false
                            ),
                            mapFeature = NavigationMapFeature(
                                zoomGestureEnabled = true,
                                userLocationEnabled = state.locationPermissionGranted,
                                tiltGestureEnabled = true,
                                rotationGestureEnabled = true
                            ),
                            interactionFilterLogic = {
                                it is NavigationMapInteractionType.Gesture ||
                                    it is NavigationMapInteractionType.Dev ||
                                    it is NavigationMapInteractionType.Rotate
                            },
                            interactionListener = { interactionType ->
                                handleEvent(FayucaFinderMapViewEvent.MapInteractedByUser(interactionType))
                            }
                        )
                        !state.userCurrentLocation.isDefault() -> NavigationMapState.UpdateCenterLocation(
                            NavigationMapCenter.Unbounded(
                                centerLocation = state.userCurrentLocation,
                                zoomLevel = MAP_ZOOM_LEVEL,
                                animate = false
                            )
                        )
                        else -> NavigationMapState.Idle
                    }
                )
            )
        )
        handleNavigationReadyState(NavigationReadyRequirement.View, ReadyState.Ready)
    }

    private fun handleNavigationReadyState(navigationReadyRequirement: NavigationReadyRequirement, readyState: ReadyState) {
        AppLog.d(TAG, "handleRiderNavigationReadyState")
        when (navigationReadyRequirement) {
            NavigationReadyRequirement.Location -> updateViewState(
                state.copy(
                    navigationViewReadyState = state.navigationViewReadyState.copy(location = readyState)
                )
            )
            NavigationReadyRequirement.View -> updateViewState(
                state.copy(
                    navigationViewReadyState = state.navigationViewReadyState.copy(view = readyState)
                )
            )
        }
        if (state.isReady()) {
            handleLocationChangedViewUpdate(state.userCurrentLocation)
        }
    }

    private fun handleMapInteractedByUser(event: FayucaFinderMapViewEvent.MapInteractedByUser) {
        AppLog.d(TAG, "handleMapInteractedByUser")
        when (event.interactionType) {
            is NavigationMapInteractionType.Gesture -> {
                updateViewState(
                    state.copy(
                        mapZoomType = MapZoomType.FreeZoom,
                        viewState = state.viewState.copy(
                            navigationMapState = NavigationMapState.Idle
                        ),
                        navigationMapCenterLocation = event.interactionType.centerLocation
                    )
                )
            }
            is NavigationMapInteractionType.Dev -> {
                updateViewState(
                    state.copy(
                        viewState = state.viewState.copy(
                            navigationMapState = NavigationMapState.Idle
                        ),
                        navigationMapCenterLocation = event.interactionType.centerLocation
                    )
                )
            }
            else -> {}
        }
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
                updateViewState(state.copy(locationPermissionGranted = true))
                handleEvent(FayucaFinderMapViewEvent.GetLocation)
            }
        }
    }

    private fun handlePermissionDenied(permission: Permission) {
        AppLog.d(TAG, "handlePermissionDenied")
        when {
            permission.permissionId.isAccessFineLocationPermission() -> {
                checkIfNotificationPermissionIsNeeded()
                updateViewState(state.copy(locationPermissionGranted = false))
            }
        }
    }

    private fun checkIfNotificationPermissionIsNeeded() {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            handleEvent(
                FayucaFinderMapViewEvent.CheckIfPermissionIsGranted(
                    permission = Permission(permissionId = Manifest.permission.POST_NOTIFICATIONS)
                )
            )
        }
    }

    private fun handleGetLocation(event: FayucaFinderMapViewEvent.GetLocation) {
        AppLog.d(TAG, "handleGetLocation")
        if (state.locationPermissionGranted) {
            viewModelScope.launch {
                getLocationUseCase.run(Unit)
                    .catch { e ->
                        AppLog.e(TAG, "handleGetLocation error", e)
                    }
                    .collectLatest { locationState ->
                        handleLocationState(locationState)
                    }
            }
        }
    }

    private fun handleStopLocation(event: FayucaFinderMapViewEvent.StopLocation) {
        AppLog.d(TAG, "handleStopLocation")
        viewModelScope.launch {
            stopLocationUseCase.run(Unit)
        }
    }

    private fun handleLocationState(locationState: LocationState) {
        AppLog.d(TAG, "handleLocationState $locationState")

        when (locationState) {
            is LocationState.LocationUpdate -> handleLocationUpdate(locationState.location)
            is LocationState.LocationPermissionDenied -> requestForPermission(
                Permission(permissionId = Manifest.permission.ACCESS_FINE_LOCATION)
            )
            is LocationState.LocationDisabled -> sendViewEffect(
                ViewEffect.ShowDialogEffect(
                    context = context,
                    title = appDictionary.toString(AppText.TranslatableText(FayucaFinderRes.string.turn_location_on)),
                    message = appDictionary.toString(AppText.TranslatableText(FayucaFinderRes.string.turn_location_on_desc)),
                    positiveButtonTitle = appDictionary.toString(AppText.TranslatableText(FayucaFinderRes.string.open_settings)),
                    positiveClickListener = { _, _ ->
                        sendViewEffect(
                            listOf(
                                ViewEffect.StartActivityEffect(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)),
                                ViewEffect.DismissDialogEffect
                            )
                        )
                    }
                )
            )
            is LocationState.LocationEnabled -> {
                sendViewEffect(ViewEffect.DismissDialogEffect)
            }
            else -> {}
        }
    }

    private fun handleLocationUpdate(location: Geolocation) {
        val isFirstLocationUpdate = state.navigationViewReadyState.isLocationNotReady()

        updateViewState(
            state.copy(
                userCurrentLocation = location,
                navigationMapCenterLocation = when (isFirstLocationUpdate) {
                    true -> location
                    false -> state.navigationMapCenterLocation
                }
            )
        )

        if (isFirstLocationUpdate) {
            handleNavigationReadyState(navigationReadyRequirement = NavigationReadyRequirement.Location, readyState = ReadyState.Ready)
        }
    }

    private fun handleLocationChangedViewUpdate(location: Geolocation) {
        AppLog.d(TAG, "handleLocationChangedViewUpdate")
        updateViewState(
            state.copy(
                viewState = state.viewState.copy(
                    navigationMapState = NavigationMapState.Show(
                        navigationMapCenter = NavigationMapCenter.Unbounded(
                            centerLocation = location,
                            zoomLevel = MAP_ZOOM_LEVEL,
                            animate = false
                        ),
                        mapFeature = NavigationMapFeature(
                            zoomGestureEnabled = true,
                            userLocationEnabled = state.locationPermissionGranted,
                            tiltGestureEnabled = true,
                            rotationGestureEnabled = true
                        ),
                        interactionFilterLogic = {
                            it is NavigationMapInteractionType.Gesture ||
                                it is NavigationMapInteractionType.Dev ||
                                it is NavigationMapInteractionType.Rotate
                        },
                        interactionListener = { interactionType ->
                            handleEvent(FayucaFinderMapViewEvent.MapInteractedByUser(interactionType))
                        }
                    )
                )
            )
        )
    }

    private fun getDefaultState(): InternalState {
        return InternalState()
    }

    data class InternalState(
        val viewState: FayucaFinderMapState = FayucaFinderMapState(),
        val navigationViewReadyState: NavigationViewReadyState = NavigationViewReadyState(),
        val navigationMapCenterLocation: Geolocation = Geolocation(),
        val mapZoomType: MapZoomType = MapZoomType.FreeZoom,
        val userCurrentLocation: Geolocation = Geolocation(),
        val locationPermissionGranted: Boolean = false
    ) {
        fun isReady(): Boolean = this.navigationViewReadyState.isReady()
    }

    companion object {
        const val TAG = "FayucaFinderMapVM"
        const val MAP_ZOOM_LEVEL = 10f
    }
}
