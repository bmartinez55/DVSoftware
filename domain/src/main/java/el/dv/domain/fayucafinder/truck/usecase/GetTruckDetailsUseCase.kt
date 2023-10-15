package el.dv.domain.fayucafinder.truck.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.fayucafinder.truck.repository.TruckDetails
import el.dv.domain.fayucafinder.truck.repository.TruckRepository

class GetTruckDetailsUseCase(private val truckRepository: TruckRepository) : SuspendUseCase<String, Result<TruckDetails>> {

    override suspend fun run(truckId: String): Result<TruckDetails> {
        return truckRepository.getTruckDetails(truckId = truckId)
    }
}
