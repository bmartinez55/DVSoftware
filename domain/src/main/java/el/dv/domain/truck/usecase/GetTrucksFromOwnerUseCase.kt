package el.dv.domain.truck.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.truck.repository.Truck
import el.dv.domain.truck.repository.TruckRepository

class GetTrucksFromOwnerUseCase(private val truckRepository: TruckRepository) : SuspendUseCase<String, Result<List<Truck>>> {

    override suspend fun run(ownerId: String): Result<List<Truck>> {
        return truckRepository.getTrucksFromOwner(ownerId = ownerId)
    }
}
