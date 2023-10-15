package el.dv.data.fayucafinder.network.truck.api

import el.dv.domain.core.Result
import el.dv.domain.fayucafinder.truck.repository.AddTruckRequest
import el.dv.domain.fayucafinder.truck.repository.Truck
import el.dv.domain.fayucafinder.truck.repository.TruckDetails
import el.dv.domain.fayucafinder.truck.repository.UpdateTruckOperationStatusRequest
import kotlinx.coroutines.flow.Flow

/**
 * Api that provides truck data
 */
interface TruckApi {
    /**
     * Add Truck
     * @param addTruckRequest
     * @return Result<Boolean>
     */
    suspend fun addTruck(addTruckRequest: AddTruckRequest): Result<Boolean>

    /**
     * Get Truck Details
     * @param truckId
     * @return Result<TruckDetails>
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
