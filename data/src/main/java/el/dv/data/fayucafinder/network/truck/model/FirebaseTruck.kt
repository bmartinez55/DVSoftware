package el.dv.data.fayucafinder.network.truck.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import el.dv.domain.extension.toIndexedMap
import el.dv.domain.fayucafinder.truck.repository.Accessibility
import el.dv.domain.fayucafinder.truck.repository.Truck
import el.dv.domain.fayucafinder.truck.repository.TruckDetails
import el.dv.domain.fayucafinder.truck.repository.TruckGenre
import el.dv.domain.fayucafinder.truck.repository.UpdateTruckOperationStatusRequest

@IgnoreExtraProperties
data class FirebaseTruck(
    val truckId: String,
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isOpen: Boolean = false,
    val startTime: String = "",
    val endTime: String = "",
    val ownerId: String = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "truckid" to truckId,
            "name" to name,
            "latitude" to latitude,
            "longitude" to longitude,
            "isOpen" to isOpen,
            "startTime" to startTime,
            "endTime" to endTime,
            "ownerId" to ownerId
        )
    }
}

@IgnoreExtraProperties
data class FirebaseTruckDetails(
    val truckId: String,
    val description: String,
    val genres: String = "",
    val accessibilities: String = "",
    val imageUriList: List<String> = emptyList(),
    val ownerId: String = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "truckId" to truckId,
            "description" to description,
            "genres" to genres,
            "accessibilities" to accessibilities,
            "imageUriList" to imageUriList.toIndexedMap(),
            "ownerId" to ownerId
        )
    }
}

@IgnoreExtraProperties
data class FirebaseUpdateTruckOperationStatusRequest(
    val isOpen: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "isOpen" to isOpen,
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}

fun Truck.toFirebaseTruck(): FirebaseTruck {
    return FirebaseTruck(
        truckId = this.truckId,
        name = this.name,
        latitude = this.location.lat,
        longitude = this.location.lon,
        isOpen = this.isOpen,
        startTime = this.serviceTime.startTime,
        endTime = this.serviceTime.endTime,
        ownerId = this.ownerId
    )
}

fun TruckDetails.toFirebaseTruckDetails(): FirebaseTruckDetails {
    return FirebaseTruckDetails(
        truckId = this.truckId,
        description = this.description,
        genres = this.genres.convertListOfTruckGenresToString(),
        accessibilities = this.accessibilities.convertListOfAccessibilitiesToString(),
        ownerId = this.ownerId
    )
}

fun UpdateTruckOperationStatusRequest.toFirebaseUpdateTruckOperationStatusRequest(): FirebaseUpdateTruckOperationStatusRequest {
    return FirebaseUpdateTruckOperationStatusRequest(
        isOpen = this.isOpen,
        latitude = this.location.lat,
        longitude = this.location.lon
    )
}

fun List<Accessibility>.convertListOfAccessibilitiesToString(): String {
    return this.joinToString(separator = ",") { it.name }
}

fun List<TruckGenre>.convertListOfTruckGenresToString(): String {
    return this.joinToString(separator = ",") { it.name }
}
