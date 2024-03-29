package el.dv.domain.fayucafinder.truck.usecase

import el.dv.domain.core.Result
import el.dv.domain.core.SuspendUseCase
import el.dv.domain.fayucafinder.truck.repository.Truck
import el.dv.domain.fayucafinder.truck.repository.TruckRepository
import kotlinx.coroutines.flow.Flow

class GetTrucksUseCase(private val truckRepository: TruckRepository) : SuspendUseCase<Unit, Flow<Result<List<Truck>>>> {

    override suspend fun run(param: Unit): Flow<Result<List<Truck>>> {
        return truckRepository.getTrucks()
    }
}
