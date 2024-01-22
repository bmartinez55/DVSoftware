package el.dv.dvproperties.feature.newproperty.state

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.Fragment
import el.dv.domain.dvproperties.propertydetails.model.AddPropertyRequest
import el.dv.domain.dvproperties.propertydetails.model.PropertyType
import el.dv.presentation.permission.Permission
import el.dv.presentation.permission.PermissionResult

data class NewPropertyViewState(
    val newPropertyState: NewPropertyState = NewPropertyState.Hide,
    val newPropertyCameraViewState: NewPropertyCameraViewState = NewPropertyCameraViewState.Hide
)

sealed class NewPropertyState {
    object Hide : NewPropertyState()

    data class Show(val address: MutableState<String>) : NewPropertyState()
}

sealed class NewPropertyViewEvent {
    data class Init(val fragment: Fragment, val context: Context) : NewPropertyViewEvent()
    data class OnAddressChanged(val address: String) : NewPropertyViewEvent()
    data class OnCityChanged(val city: String) : NewPropertyViewEvent()
    data class OnStateChanged(val state: String) : NewPropertyViewEvent()
    data class OnZipCodeChanged(val zipCode: String) : NewPropertyViewEvent()
    data class OnBedroomDropDownClick(val bedRooms: String) : NewPropertyViewEvent()
    data class OnBathroomDropDownClick(val bathRooms: String) : NewPropertyViewEvent()
    data class OnPropertyTypeDropDownClick(val propertyType: String) : NewPropertyViewEvent()
    data class OnPropertySizeChanged(val propertySize: String) : NewPropertyViewEvent()
    data class OnLotSizeChanged(val lotSize: String) : NewPropertyViewEvent()
    data class OnPropertyCostChanged(val propertyCost: String) : NewPropertyViewEvent()
    object OnBackClick : NewPropertyViewEvent()
    object OnSubmitButtonClick : NewPropertyViewEvent()
    data class RequestPermission(val permission: Permission) : NewPropertyViewEvent()
    data class RequestedPermissionResult(val permissionResult: PermissionResult) : NewPropertyViewEvent()
    data class TakeAPhotoButtonClick(val context: Context) : NewPropertyViewEvent()
    object UploadButtonClick : NewPropertyViewEvent()
}

sealed class NewPropertyCameraViewState {
    object Hide : NewPropertyCameraViewState()
    object Show : NewPropertyCameraViewState()
}
data class NewPropertyDetails(
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val propertyCost: String = "",
    val lotSize: String = "",
    val propertySize: String = "",
    val buildDate: String = "",
    val bedroomCount: String = "",
    val bathroomCount: String = "",
    val propertyType: PropertyType = PropertyType.SFH
)

data class NewPropertiesDetailsState(
    val addressState: MutableState<String> = mutableStateOf(""),
    val cityState: MutableState<String> = mutableStateOf(""),
    val countryState: MutableState<String> = mutableStateOf(""),
    val zipCodeState: MutableState<String> = mutableStateOf(""),
    val bedRoomState: MutableState<String> = mutableStateOf(""),
    val bathRoomState: MutableState<String> = mutableStateOf(""),
    val propertySizeState: MutableState<String> = mutableStateOf(""),
    val lotSizeState: MutableState<String> = mutableStateOf(""),
    val propertyCostState: MutableState<String> = mutableStateOf(""),
    val buildDateState: MutableState<String> = mutableStateOf(""),
    val propertyType: MutableState<PropertyType> = mutableStateOf(PropertyType.SFH)
)

fun NewPropertiesDetailsState.toAddPropertyRequest(): AddPropertyRequest {
    return AddPropertyRequest(
        address = this.addressState.value,
        city = this.cityState.value,
        state = this.countryState.value,
        zipCode = this.zipCodeState.value,
        propertyCost = this.propertyCostState.value,
        lotSize = this.lotSizeState.value,
        propertySize = this.propertySizeState.value,
        buildDate = this.buildDateState.value,
        bedroomCount = this.bedRoomState.value,
        bathroomCount = this.bathRoomState.value,
        propertyType = this.propertyType.value
    )
}
