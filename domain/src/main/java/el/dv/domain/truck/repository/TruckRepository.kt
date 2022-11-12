package el.dv.domain.truck.repository

import el.dv.domain.core.Geolocation
import el.dv.domain.core.Result
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides truck data
 */
interface TruckRepository {
    /**
     * Add user
     * @param addTruckRequest
     * @return Result<Boolean>
     */
    suspend fun addTruck(addTruckRequest: AddTruckRequest): Result<Boolean>

    /**
     * Get User by email
     * @param email: String
     * @return Result<User>
     */
    suspend fun getTruckDetails(truckId: String): Result<TruckDetails>

    /**
     * Get list of trucks
     * @return Result<List<Truck>>
     */
    suspend fun getTrucks(): Flow<Result<List<Truck>>>

    /**
     * Get list of trucks from owner
     * @param ownerId: String
     * @return Result<List<Truck>>
     */
    suspend fun getTrucksFromOwner(ownerId: String): Result<List<Truck>>

    /**
     * Update truck service operation status
     * @param updateTruckOperationStatusRequest
     * @return Result<Boolean>
     */
    suspend fun updateTruckOperationStatus(updateTruckOperationStatusRequest: UpdateTruckOperationStatusRequest): Result<Boolean>
}

data class Truck(
    val truckId: String,
    val name: String = "",
    val location: Geolocation = Geolocation(),
    val isOpen: Boolean = false,
    val serviceTime: ServiceTime = ServiceTime(),
    val ownerId: String = ""
)

data class TruckDetails(
    val truckId: String,
    val description: String,
    val genres: List<TruckGenre> = emptyList(),
    val accessibilities: List<Accessibility> = emptyList(),
    val ownerId: String = ""
)

data class AddTruckRequest(
    val truck: Truck,
    val truckDetails: TruckDetails
)

data class UpdateTruckOperationStatusRequest(
    val ownerId: String = "",
    val truckId: String = "",
    val isOpen: Boolean = false,
    val location: Geolocation = Geolocation()
)

enum class TruckGenre {
    Unspecified,
    Mexican
}

data class ServiceTime(
    val startTime: String = "",
    val endTime: String = ""
)

enum class Accessibility {
    Unspecified,
    WheelChair
}
