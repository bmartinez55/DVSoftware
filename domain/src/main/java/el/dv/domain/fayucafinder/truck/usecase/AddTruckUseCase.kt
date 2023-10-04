package el.dv.domain.fayucafinder.truck.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.fayucafinder.truck.repository.AddTruckRequest
import el.dv.domain.fayucafinder.truck.repository.TruckRepository

class AddTruckUseCase(private val truckRepository: TruckRepository) : SuspendUseCase<AddTruckRequest, Result<Boolean>> {

    override suspend fun run(addTruckRequest: AddTruckRequest): Result<Boolean> {
        return truckRepository.addTruck(addTruckRequest = addTruckRequest)
    }
}
