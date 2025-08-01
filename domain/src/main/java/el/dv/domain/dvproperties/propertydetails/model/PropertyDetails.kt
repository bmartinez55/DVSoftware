package el.dv.domain.dvproperties.propertydetails.model

import android.os.Parcelable
import androidx.room.Ignore
import el.dv.domain.core.Geolocation
import kotlinx.parcelize.Parcelize

@Parcelize
data class PropertyDetails(
    val id: Int,
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
    val propertyType: PropertyType = PropertyType.SFH,
    val imagePaths: ImagePaths = ImagePaths(),
    val coordinates: Geolocation = Geolocation()
) : Parcelable {
    fun fullAddress(): String = address.plus(", ").plus(city).plus(" ").plus(state).plus(" ").plus(zipCode)
}

enum class PropertyType {
    SFH,
    Multi,
    Commercial
}

fun String.toPropertyType(): PropertyType {
    return when (this) {
        PropertyType.SFH.name -> PropertyType.SFH
        PropertyType.Commercial.name -> PropertyType.Commercial
        PropertyType.Multi.name -> PropertyType.Multi
        else -> PropertyType.SFH
    }
}

data class AddPropertyRequest(
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
    val propertyType: PropertyType = PropertyType.SFH,
    @Ignore
    var imagePaths: ImagePaths = ImagePaths(),
    val coordinates: Geolocation = Geolocation()
)
