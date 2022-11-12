package el.dv.data.network.truck.api

import el.dv.domain.core.Result
import el.dv.domain.truck.repository.AddTruckRequest
import el.dv.domain.truck.repository.Truck
import el.dv.domain.truck.repository.TruckDetails
import el.dv.domain.truck.repository.UpdateTruckOperationStatusRequest
import kotlinx.coroutines.flow.Flow

/**
 * Api that provides truck services
 */
interface TruckApi {
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
