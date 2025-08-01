package el.dv.dvproperties.feature.newproperty.view

import android.Manifest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import el.dv.domain.core.Geolocation
import el.dv.domain.core.ifFailure
import el.dv.domain.core.ifSuccess
import el.dv.domain.dvproperties.geocoding.usecase.GetPropertyCoordinatesUseCase
import el.dv.domain.dvproperties.propertydetails.model.toPropertyType
import el.dv.domain.dvproperties.propertydetails.usecase.AddNewPropertyUseCase
import el.dv.domain.logging.AppLog
import el.dv.domain.sharedpreferences.model.LoadValueRequest
import el.dv.domain.sharedpreferences.usecase.LoadBooleanFromSharedPreferencesUseCase
import el.dv.dvproperties.feature.newproperty.state.NewPropertiesDetailsState
import el.dv.dvproperties.feature.newproperty.state.NewPropertyCameraViewState
import el.dv.dvproperties.feature.newproperty.state.NewPropertyState
import el.dv.dvproperties.feature.newproperty.state.NewPropertyViewEvent
import el.dv.dvproperties.feature.newproperty.state.NewPropertyViewState
import el.dv.dvproperties.feature.newproperty.state.toAddPropertyRequest
import el.dv.dvpropertiesdata.util.DVPropertiesConst
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionApi
import el.dv.presentation.permission.PermissionFactory
import el.dv.presentation.permission.PermissionResult
import el.dv.presentation.permission.PermissionResultState
import el.dv.presentation.permission.RequestPermissionCallback
import el.dv.presentation.permission.UIPermissionProviderConstructParams
import el.dv.presentation.permission.usecase.CheckPermissionGrantedUseCase
import el.dv.presentation.permission.usecase.RequestForPermissionGrantedUseCase
import el.dv.presentation.util.AppDictionary
import el.dv.presentation.util.AppText
import el.dv.presentation.view.effect.ViewEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import el.dv.presentation.R as PresentationR

class NewPropertyViewModel(
    private val addNewPropertyUseCase: AddNewPropertyUseCase,
    private val loadBooleanFromSharedPreferencesUseCase: LoadBooleanFromSharedPreferencesUseCase,
    private val getPropertyCoordinatesUseCase: GetPropertyCoordinatesUseCase,
    private val permissionFactory: PermissionFactory<UIPermissionProviderConstructParams<RequestPermissionCallback>, PermissionApi, CheckPermissionGrantedUseCase, RequestForPermissionGrantedUseCase>,
    private val appDictionary: AppDictionary
) : ViewModel() {

    private val internalState = MutableLiveData(InternalState())
    private val state: InternalState
        get() = requireNotNull(internalState.value)

    val viewState: LiveData<NewPropertyViewState> = internalState.map { it.viewState }.distinctUntilChanged()

    private val internalViewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = internalViewEffect

    private val eventChannel = Channel<NewPropertyViewEvent>(Channel.UNLIMITED)

    // Permission instances
    private lateinit var permissionApi: PermissionApi
    private lateinit var checkPermissionGrantedUseCase: CheckPermissionGrantedUseCase
    private lateinit var requestForPermissionGrantedUseCase: RequestForPermissionGrantedUseCase

    private val permissionResultState = PermissionResultState()

    val newPropertiesDetailsState = NewPropertiesDetailsState()

    init {
        eventChannel
            .consumeAsFlow()
            .catch { e -> AppLog.d("Event catch $e") }
            .onEach { event ->
                when (event) {
                    is NewPropertyViewEvent.Init -> handleInit(event)
                    is NewPropertyViewEvent.OnBackClick -> handleOnBackClick(event)
                    is NewPropertyViewEvent.OnSubmitButtonClick -> handleOnSubmitButtonClick(event)
                    is NewPropertyViewEvent.TakeAPhotoButtonClick -> handleTakeAPhotoButtonClick(event)
                    is NewPropertyViewEvent.RequestPermission -> handleRequestPermission(event)
                    is NewPropertyViewEvent.RequestedPermissionResult -> handleRequestedPermissionResult(event)
                    is NewPropertyViewEvent.OnAddressChanged -> handleOnAddressChanged(event)
                    is NewPropertyViewEvent.OnCityChanged -> handleOnCityChanged(event)
                    is NewPropertyViewEvent.OnStateChanged -> handleOnStateChanged(event)
                    is NewPropertyViewEvent.OnZipCodeChanged -> handleOnZipCodeChanged(event)
                    is NewPropertyViewEvent.OnBedroomDropDownClick -> handleOnBedroomDropDownClick(event)
                    is NewPropertyViewEvent.OnBathroomDropDownClick -> handleOnBathroomDropDownClick(event)
                    is NewPropertyViewEvent.OnPropertyTypeDropDownClick -> handleOnPropertyTypeDropDownClick(event)
                    is NewPropertyViewEvent.OnPropertySizeChanged -> handleOnPropertySizeChanged(event)
                    is NewPropertyViewEvent.OnPropertyCostChanged -> handleOnPropertyCostChanged(event)
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun handleViewEvent(event: NewPropertyViewEvent) {
        AppLog.d(TAG, "handleViewEvent $event")
        eventChannel.trySend(event)
    }

    private fun sendViewEffect(viewEffect: ViewEffect) {
        AppLog.d(TAG, "sendViewEffect")
        internalViewEffect.value = viewEffect
    }

    private fun updateViewState(state: InternalState) {
        internalState.value = state
    }

    private fun handleInit(event: NewPropertyViewEvent.Init) {
        AppLog.d(TAG, "handleInit")
        val requestPermissionCallback = RequestPermissionCallback {
            handleViewEvent(NewPropertyViewEvent.RequestedPermissionResult(it))
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
        getAllPermissionStatus()
    }

    private fun getAllPermissionStatus() {
        AppLog.d(TAG, "getAllPermissionStatus")
        viewModelScope.launch {
            permissionResultState.isCameraPermissionGranted.value = loadBooleanFromSharedPreferencesUseCase.run(LoadValueRequest(DVPropertiesConst.CAMERA_PERMISSION_GRANTED))
            permissionResultState.isImageAccessPermissionGranted.value = loadBooleanFromSharedPreferencesUseCase.run(LoadValueRequest(DVPropertiesConst.IMAGE_ACCESS_PERMISSION_GRANTED))
            permissionResultState.isRecordAudioPermissionGranted.value = loadBooleanFromSharedPreferencesUseCase.run(LoadValueRequest(DVPropertiesConst.RECORD_AUDIO_PERMISSION_GRANTED))
            permissionResultState.isVideoAccessPermissionGranted.value = loadBooleanFromSharedPreferencesUseCase.run(LoadValueRequest(DVPropertiesConst.VIDEO_ACCESS_PERMISSION_GRANTED))
            updateViewState(state.copy(viewState = state.viewState.copy(newPropertyState = NewPropertyState.Show(newPropertiesDetailsState.addressState))))
            AppLog.d(TAG, "isCameraPermissionGranted? ${permissionResultState.isCameraPermissionGranted}")
        }
    }

    private fun handleOnAddressChanged(event: NewPropertyViewEvent.OnAddressChanged) {
        AppLog.d(TAG, "handleOnAddressChanged")
        newPropertiesDetailsState.addressState.value = event.address
    }

    private fun handleOnCityChanged(event: NewPropertyViewEvent.OnCityChanged) {
        AppLog.d(TAG, "handleOnCityChanged")
        newPropertiesDetailsState.cityState.value = event.city
    }

    private fun handleOnStateChanged(event: NewPropertyViewEvent.OnStateChanged) {
        AppLog.d(TAG, "handleOnStateChanged")
        newPropertiesDetailsState.countryState.value = event.state
    }

    private fun handleOnZipCodeChanged(event: NewPropertyViewEvent.OnZipCodeChanged) {
        AppLog.d(TAG, "handleOnZipCodeChanged")
        newPropertiesDetailsState.zipCodeState.value = event.zipCode
    }

    private fun handleOnBedroomDropDownClick(event: NewPropertyViewEvent.OnBedroomDropDownClick) {
        AppLog.d(TAG, "handleOnBedroomDropDownClick")
        newPropertiesDetailsState.bedRoomState.value = event.bedRooms
    }

    private fun handleOnBathroomDropDownClick(event: NewPropertyViewEvent.OnBathroomDropDownClick) {
        AppLog.d(TAG, "handleOnBathroomDropDownClick")
        newPropertiesDetailsState.bathRoomState.value = event.bathRooms
    }

    private fun handleOnPropertyTypeDropDownClick(event: NewPropertyViewEvent.OnPropertyTypeDropDownClick) {
        AppLog.d(TAG, "handleOnPropertyTypeDropDownClick")
        newPropertiesDetailsState.propertyType.value = event.propertyType.toPropertyType()
    }

    private fun handleOnPropertySizeChanged(event: NewPropertyViewEvent.OnPropertySizeChanged) {
        AppLog.d(TAG, "handleOnPropertySizeChanged")
        newPropertiesDetailsState.propertySizeState.value = event.propertySize
    }

    private fun handleOnPropertyCostChanged(event: NewPropertyViewEvent.OnPropertyCostChanged) {
        AppLog.d(TAG, "handleOnPropertyCostChanged")
        newPropertiesDetailsState.propertyCostState.value = event.propertyCost
    }

    private fun handleOnBackClick(event: NewPropertyViewEvent.OnBackClick) {
        AppLog.d(TAG, "handleOnBackClick")
        sendViewEffect(ViewEffect.NavigateBack)
    }

    private fun handleOnSubmitButtonClick(event: NewPropertyViewEvent.OnSubmitButtonClick) {
        AppLog.d(TAG, "handleOnSubmitButtonClick")
        viewModelScope.launch {
            getPropertyCoordinatesUseCase.run(newPropertiesDetailsState.addressState.value).ifSuccess { coordinates ->
                addNewProperty(coordinates)
            }.ifFailure { e ->
                AppLog.d(TAG, "getPropertyCoordinatesUseCase error $e")
                addNewProperty()
            }
        }
    }

    private fun addNewProperty(propertyCoordinates: Geolocation = Geolocation()) {
        AppLog.d(TAG, "addNewProperty")
        viewModelScope.launch {
            addNewPropertyUseCase.run(newPropertiesDetailsState.toAddPropertyRequest(propertyCoordinates)).ifSuccess {
                sendViewEffect(ViewEffect.NavigateBack)
            }.ifFailure { e -> AppLog.d(TAG, "Error submitting new property $e") }
        }
    }

    private fun handleTakeAPhotoButtonClick(event: NewPropertyViewEvent.TakeAPhotoButtonClick) {
        AppLog.d(TAG, "handleTakeAPhotoButtonClick")
        when (permissionResultState.isCameraPermissionGranted.value) {
            true -> updateViewState(state.copy(state.viewState.copy(newPropertyCameraViewState = NewPropertyCameraViewState.Show)))
            false -> sendViewEffect(
                ViewEffect.ShowDialogEffect(
                    event.context,
                    title = appDictionary.toString(AppText.TranslatableText(PresentationR.string.camera_permission_denied)),
                    message = appDictionary.toString(AppText.TranslatableText(PresentationR.string.camera_permission_denied_desc)),
                    positiveButtonTitle = appDictionary.toString(AppText.TranslatableText(PresentationR.string.request)),
                    positiveClickListener = { _, _ ->
                        handleViewEvent(NewPropertyViewEvent.RequestPermission(Permission(Manifest.permission.CAMERA)))
                    },
                    negativeButtonTitle = appDictionary.toString(AppText.TranslatableText(PresentationR.string.back)),
                    negativeClickListener = { _, _ ->
                        sendViewEffect(ViewEffect.DismissDialogEffect)
                    }
                )
            )
        }
    }

    private fun handleRequestPermission(event: NewPropertyViewEvent.RequestPermission) {
        AppLog.d(TAG, "handleRequestPermission")
        viewModelScope.launch {
            requestForPermissionGrantedUseCase.run(event.permission)
        }
    }

    private fun handleRequestedPermissionResult(event: NewPropertyViewEvent.RequestedPermissionResult) {
        AppLog.d(TAG, "handleRequestedPermissionResult")
        when (event.permissionResult) {
            is PermissionResult.Granted -> handlePermissionGranted(event.permissionResult.permission)
            is PermissionResult.Denied -> {}
        }
    }

    private fun handlePermissionGranted(permission: Permission) {
        AppLog.d(TAG, "handlePermissionGranted $permission")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                permission.isImageAccessPermission() -> permissionResultState.isImageAccessPermissionGranted.value = true
                permission.isVideoAccessPermission() -> permissionResultState.isVideoAccessPermissionGranted.value = true
            }
        }

        when {
            permission.isPermissionCamera() -> permissionResultState.isCameraPermissionGranted.value = true
            permission.isPermissionRecordAudio() -> permissionResultState.isRecordAudioPermissionGranted.value = true
        }
    }

    data class InternalState(
        val viewState: NewPropertyViewState = NewPropertyViewState()
    )

    companion object {
        const val TAG = "NewPropertyViewModel"
    }
}
