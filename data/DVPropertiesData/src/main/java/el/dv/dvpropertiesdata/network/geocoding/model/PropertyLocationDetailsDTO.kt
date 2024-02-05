package el.dv.dvpropertiesdata.network.geocoding.model

import com.google.gson.annotations.SerializedName

data class PropertyLocationDetailsDTO(
    @SerializedName("hits")
    val hits: List<LocationDetailsDTO>
)

data class LocationDetailsDTO(
    @SerializedName("point")
    val points: List<LocationCoordinatesDTO>
)

data class LocationCoordinatesDTO(
    @SerializedName("lng")
    val longitude: Double,
    @SerializedName("lat")
    val latitude: Double
)
