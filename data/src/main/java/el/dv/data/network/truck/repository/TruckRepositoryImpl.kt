package el.dv.data.network.truck.repository

import el.dv.data.network.truck.api.TruckApi
import el.dv.domain.core.CoroutineDispatchers
import el.dv.domain.core.Result
import el.dv.domain.truck.repository.AddTruckRequest
import el.dv.domain.truck.repository.Truck
import el.dv.domain.truck.repository.TruckDetails
import el.dv.domain.truck.repository.TruckRepository
import el.dv.domain.truck.repository.UpdateTruckOperationStatusRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Repository implementation that provides truck data
 */
class TruckRepositoryImpl(
    private val truckApi: TruckApi,
    private val dispatchers: CoroutineDispatchers
) : TruckRepository {

    override suspend fun addTruck(addTruckRequest: AddTruckRequest): Result<Boolean> = withContext(dispatchers.IO) {
        truckApi.addTruck(addTruckRequest = addTruckRequest)
    }

    override suspend fun getTruckDetails(truckId: String): Result<TruckDetails> = withContext(dispatchers.IO) {
        truckApi.getTruckDetails(truckId = truckId)
    }

    override suspend fun getTrucks(): Flow<Result<List<Truck>>> {
        return truckApi.getTrucks().flowOn(dispatchers.IO)
    }

    override suspend fun getTrucksFromOwner(ownerId: String): Result<List<Truck>> = withContext(dispatchers.IO) {
        truckApi.getTrucksFromOwner(ownerId = ownerId)
    }

    override suspend fun updateTruckOperationStatus(updateTruckOperationStatusRequest: UpdateTruckOperationStatusRequest): Result<Boolean> = withContext(dispatchers.IO) {
        truckApi.updateTruckOperationStatus(updateTruckOperationStatusRequest = updateTruckOperationStatusRequest)
    }
}
