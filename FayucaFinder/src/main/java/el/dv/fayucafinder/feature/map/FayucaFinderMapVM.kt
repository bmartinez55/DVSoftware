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
import el.dv.domain.event.AppEvent
import el.dv.domain.event.EventBus
import el.dv.domain.location.LocationState
import el.dv.domain.logging.AppLog
import el.dv.domain.navigation.model.MapConfigurations
import el.dv.domain.navigation.model.MapVisualType
import el.dv.domain.navigation.model.MapZoomType
import el.dv.domain.navigation.model.NavigationMapFeature
import el.dv.domain.navigation.model.NavigationMapInteractionType
import el.dv.domain.networkmonitor.model.NetworkState
import el.dv.domain.networkmonitor.usecase.StartNetworkConnectivityMonitorUseCase
import el.dv.domain.sharedpreferences.model.LoadValueRequest
import el.dv.domain.sharedpreferences.model.SaveStringRequest
import el.dv.domain.sharedpreferences.usecase.LoadStringFromSharedPreferencesUseCase
import el.dv.domain.sharedpreferences.usecase.SaveStringInSharedPreferencesUseCase
import el.dv.fayucafinder.extension.toMapVisualType
import el.dv.fayucafinder.feature.map.viewreducer.GetNavigationMapCenterLocationUpdateViewReducer
import el.dv.fayucafinder.util.Const
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
import el.dv.presentation.R as PresentationRes
import el.dv.xml_uikit.R as XmlUiKitRes

class FayucaFinderMapVM(
    private val getLocationUseCase: GetLocationUseCase,
    private val stopLocationUseCase: StopLocationUseCase,
    private val saveStringInSharedPreferencesUseCase: SaveStringInSharedPreferencesUseCase,
    private val loadStringFromSharedPreferencesUseCase: LoadStringFromSharedPreferencesUseCase,
    private val startNetworkConnectivityMonitorUseCase: StartNetworkConnectivityMonitorUseCase,
    private val getNavigationMapCenterLocationUpdateViewReducer: GetNavigationMapCenterLocationUpdateViewReducer,
    private val permissionFactory: PermissionFactory<UIPermissionProviderConstructParams<RequestPermissionCallback>, PermissionApi, CheckPermissionGrantedUseCase, RequestForPermissionGrantedUseCase>,
    private val appDictionary: AppDictionary,
    private val eventBus: EventBus,
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
        observeLocalViewEventChannel()
        observeGlobalEventBus()
    }

    private fun observeLocalViewEventChannel() {
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
                    is FayucaFinderMapViewEvent.CurrentLocationMenuClick -> handleCurrentLocationMenuClicked(event)
                    is FayucaFinderMapViewEvent.MapConfigurationMenuClick -> handleMapConfigurationMenuClick(event)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeGlobalEventBus() {
        viewModelScope.launch {
            eventBus.events
                .collectLatest { event ->
                    AppLog.d(TAG, "EventBus app event: ${event.javaClass.name}")
                    when (event) {
                        is AppEvent.MapVisualTypeChangedReceived -> handleMapVisualTypeChangedReceived(event.mapVisualType)
                        else -> {}
                    }
                }
        }
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
        monitorNetworkConnectivity()
    }

    private fun monitorNetworkConnectivity() {
        AppLog.d(TAG, "monitorNetworkConnectivity")
        viewModelScope.launch {
            startNetworkConnectivityMonitorUseCase.run(Unit)
                .catch { e ->
                    AppLog.e(TAG, "monitorNetworkConnectivity catch error", e)
                }
                .collectLatest { networkState ->
                    AppLog.d(TAG, "networkState: $networkState")
                    when (networkState) {
                        is NetworkState.Connected -> updateViewState(
                            state.copy(
                                viewState = state.viewState.copy(
                                    bottomBannerViewState = BottomBannerViewState.Hide,
                                    navigationMapState = NavigationMapState.Idle
                                )
                            )
                        )
                        is NetworkState.Disconnected -> updateViewState(
                            state.copy(
                                viewState = state.viewState.copy(
                                    bottomBannerViewState = BottomBannerViewState.ShowWifiDisconnected(
                                        drawableRes = XmlUiKitRes.drawable.ic_wifi_off_icon,
                                        title = appDictionary.toString(AppText.TranslatableText(PresentationRes.string.wifi_disconnected))
                                    ),
                                    navigationMapState = NavigationMapState.Idle
                                )
                            )
                        )
                    }
                }
        }
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
        viewModelScope.launch {
            val storedMapVisualType = loadStringFromSharedPreferencesUseCase.run(LoadValueRequest(Const.MAP_VISUAL_TYPE_KEY)).toMapVisualType()
            val mapConfigurations = MapConfigurations(
                mapFeature = NavigationMapFeature(
                    zoomGestureEnabled = true,
                    userLocationEnabled = state.locationPermissionGranted,
                    tiltGestureEnabled = true,
                    rotationGestureEnabled = true,
                    buildingEnabled = storedMapVisualType == MapVisualType.ThreeDimension
                ),
                mapVisualType = storedMapVisualType
            )
            updateViewState(
                state.copy(
                    viewState = state.viewState.copy(
                        navigationMapState = when {
                            state.isReady() -> NavigationMapState.Show(
                                navigationMapCenter = NavigationMapCenter.Unbounded(
                                    centerLocation = state.userCurrentLocation,
                                    zoomLevel = if (mapConfigurations.isIn3DMode()) Const.THREE_DIMENSION_ZOOM_LEVEL
                                    else Const.CURRENT_LOCATION_SELECTION_ZOOM_LEVEL,
                                    animate = mapConfigurations.isIn3DMode(),
                                    tilt = if (mapConfigurations.isIn3DMode()) Const.THREE_DIMENSION_CAMERA_TILT else 0f
                                ),
                                mapFeature = mapConfigurations.mapFeature,
                                interactionFilterLogic = {
                                    it is NavigationMapInteractionType.Gesture ||
                                        it is NavigationMapInteractionType.Dev ||
                                        it is NavigationMapInteractionType.Rotate
                                },
                                interactionListener = { interactionType ->
                                    handleEvent(FayucaFinderMapViewEvent.MapInteractedByUser(interactionType))
                                },
                                mapVisualType = mapConfigurations.mapVisualType
                            )
                            !state.userCurrentLocation.isDefault() -> NavigationMapState.UpdateCenterLocation(
                                NavigationMapCenter.Unbounded(
                                    centerLocation = state.userCurrentLocation,
                                    zoomLevel = Const.CURRENT_LOCATION_SELECTION_ZOOM_LEVEL,
                                    animate = false
                                )
                            )
                            else -> NavigationMapState.Idle
                        },
                        currentLocationMenuState = CurrentLocationMenuState.Show,
                        mapConfigurationMenuState = MapConfigurationMenuState.Show
                    ),
                    mapConfigurations = mapConfigurations
                )
            )
            handleNavigationReadyState(NavigationReadyRequirement.View, ReadyState.Ready)
        }
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

    private fun handleCurrentLocationMenuClicked(event: FayucaFinderMapViewEvent.CurrentLocationMenuClick) {
        AppLog.d(TAG, "handleCurrentLocationMenuClicked")
        val currentLocationMapState = getNavigationMapCenterLocationUpdateViewReducer.run(
            GetNavigationMapCenterLocationUpdateViewReducer.Request(state)
        )
        updateViewState(currentLocationMapState.state)
    }

    private fun handleMapConfigurationMenuClick(event: FayucaFinderMapViewEvent.MapConfigurationMenuClick) {
        AppLog.d(TAG, "handleMapConfigurationMenuClick")
        sendViewEffect(ViewEffect.ShowMapConfigurationsScreenEffect(state.mapConfigurations.mapVisualType))
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
        val mapFeature = state.mapConfigurations.mapFeature.copy(
            zoomGestureEnabled = true,
            userLocationEnabled = state.locationPermissionGranted,
            tiltGestureEnabled = true,
            rotationGestureEnabled = true,
            buildingEnabled = state.mapConfigurations.isIn3DMode()
        )
        updateViewState(
            state.copy(
                viewState = state.viewState.copy(
                    navigationMapState = NavigationMapState.Show(
                        navigationMapCenter = NavigationMapCenter.Unbounded(
                            centerLocation = location,
                            zoomLevel = if (state.mapConfigurations.isIn3DMode()) Const.THREE_DIMENSION_ZOOM_LEVEL
                            else Const.CURRENT_LOCATION_SELECTION_ZOOM_LEVEL,
                            animate = state.mapConfigurations.isIn3DMode(),
                            tilt = if (state.mapConfigurations.isIn3DMode()) Const.THREE_DIMENSION_CAMERA_TILT else 0f
                        ),
                        mapFeature = mapFeature,
                        interactionFilterLogic = {
                            it is NavigationMapInteractionType.Gesture ||
                                it is NavigationMapInteractionType.Dev ||
                                it is NavigationMapInteractionType.Rotate
                        },
                        interactionListener = { interactionType ->
                            handleEvent(FayucaFinderMapViewEvent.MapInteractedByUser(interactionType))
                        },
                        mapVisualType = state.mapConfigurations.mapVisualType
                    )
                ),
                mapConfigurations = state.mapConfigurations.copy(mapFeature = mapFeature)
            )
        )
    }

    /**
     * Below this is Global Event Bus related methods
     */

    private fun handleMapVisualTypeChangedReceived(mapVisualType: MapVisualType) {
        AppLog.d(TAG, "handleMapVisualTypeChangedReceived")
        updateViewState(state.copy(mapConfigurations = state.mapConfigurations.copy(mapVisualType = mapVisualType)))
        sendViewEffect(ViewEffect.UpdateMapTypeEffect(mapVisualType = mapVisualType))
        val mapVisualTypeState = getNavigationMapCenterLocationUpdateViewReducer.run(
            GetNavigationMapCenterLocationUpdateViewReducer.Request(state)
        )
        updateViewState(mapVisualTypeState.state)
        viewModelScope.launch {
            saveStringInSharedPreferencesUseCase.run(SaveStringRequest(Const.MAP_VISUAL_TYPE_KEY, mapVisualType.name))
        }
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
        val locationPermissionGranted: Boolean = false,
        val mapConfigurations: MapConfigurations = MapConfigurations()
    ) {
        fun isReady(): Boolean = this.navigationViewReadyState.isReady()
    }

    companion object {
        const val TAG = "FayucaFinderMapVM"
    }
}
